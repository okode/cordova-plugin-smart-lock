import Foundation
import KeychainAccess

class Credentials: NSObject, NSCoding {
    
    public static let ID_PARAM = "id"
    public static let NAME_PARAM = "name"
    public static let PASSWORD_PARAM = "password"

    var id: String?
    var name: String?
    var password: String?
    
    required init?(coder: NSCoder) {
        self.id = coder.decodeObject(forKey: Credentials.ID_PARAM) as? String
        self.name = coder.decodeObject(forKey: Credentials.NAME_PARAM) as? String
        self.password = coder.decodeObject(forKey: Credentials.PASSWORD_PARAM) as? String
    }
    
    init(id: String, name: String, password:String) {
        self.id = id
        self.name = name
        self.password = password
    }
    
    func encode(with coder: NSCoder) {
        if let id = id { coder.encode(id, forKey: Credentials.ID_PARAM) }
        if let name = name { coder.encode(name, forKey: Credentials.NAME_PARAM) }
        if let password = password { coder.encode(password, forKey: Credentials.PASSWORD_PARAM) }
    }
    
    static func parse(args: [Any]?) -> Credentials? {
        if (args == nil || args!.count == 0) { return nil }
        if let json = args![0] as? [String: String],
            let id = json[Credentials.ID_PARAM],
            let name = json[Credentials.NAME_PARAM],
            let password = json[Credentials.PASSWORD_PARAM] {
            return Credentials(id: id, name: name, password: password)
        }
        return nil
    }

}

@objc(Smartlock) class Smartlock : CDVPlugin {
    
    private static let KEYCHAIN_GROUP_NAME = "smartlock"
    private static let CREDENTIALS_KEYCHAIN_KEY = "smartlock_credentials"
    
    enum PluginError:Int {
        case REQUEST_CREDENTIALS_NOT_FOUND = -100
        case SAVE_OP_ERROR = -200
        case SAVE_BAD_REQUEST_ERROR = -201
        case DELETE_OP_ERROR = -300
    }

    override func pluginInitialize() {
        super.pluginInitialize()
    }

    @objc(save:)
    func save(_ command: CDVInvokedUrlCommand) {
        if let credentials = Credentials.parse(args: command.arguments) {
            let keychain = self.getSharedKeyChainInstance()
            do {
                let credentialsData = NSKeyedArchiver.archivedData(withRootObject: credentials)
                try keychain.set(credentialsData, key: Smartlock.CREDENTIALS_KEYCHAIN_KEY)
                self.sendSuccess(callbackId: command.callbackId)
            }
            catch let error {
                self.sendError(error: PluginError.SAVE_OP_ERROR, message: error.localizedDescription, callbackId: command.callbackId)
            }
        } else {
            self.sendError(error: PluginError.SAVE_BAD_REQUEST_ERROR, message: "No credentials provided", callbackId: command.callbackId)
        }
    }

    @objc(request:)
    func request(_ command: CDVInvokedUrlCommand){
        let keychain = self.getSharedKeyChainInstance()
        do {
            // Getting credentials from Keychain
            if let credentialsData = try keychain.getData(Smartlock.CREDENTIALS_KEYCHAIN_KEY) {
                // Parsing NSData => Credentials
                if let credentials = NSKeyedUnarchiver.unarchiveObject(with: credentialsData) as? Credentials {
                    // Credentials retrieved from Keychain
                    self.sendSuccess(data: [
                        Credentials.ID_PARAM: credentials.id ?? "",
                        Credentials.NAME_PARAM: credentials.name ?? "",
                        Credentials.PASSWORD_PARAM: credentials.password ?? ""
                    ], callbackId: command.callbackId)
                } else {
                    self.sendError(error: PluginError.REQUEST_CREDENTIALS_NOT_FOUND, message: "Error parsing credentials got from keychain", callbackId: command.callbackId)
                }
            } else {
                self.sendError(error: PluginError.REQUEST_CREDENTIALS_NOT_FOUND, message: "No credentials", callbackId: command.callbackId)
            }
        }
        catch let error {
            self.sendError(error: PluginError.REQUEST_CREDENTIALS_NOT_FOUND, message: error.localizedDescription, callbackId: command.callbackId)
        }

    }

    @objc(delete:)
    func delete(_ command: CDVInvokedUrlCommand){
        let keychain = self.getSharedKeyChainInstance()
        do {
            try keychain.remove(Smartlock.CREDENTIALS_KEYCHAIN_KEY)
            let result = CDVPluginResult.init(status: CDVCommandStatus.ok)
            self.commandDelegate.send(result, callbackId: command.callbackId)
        }
        catch let error {
            self.sendError(error: PluginError.DELETE_OP_ERROR, message: error.localizedDescription, callbackId: command.callbackId)
        }
    }
    
    private func getSharedKeyChainInstance() -> Keychain {
        let teamId = self.commandDelegate.settings["smartlock.teamId"] as? String ?? ""
        return Keychain(accessGroup: "\(teamId).\(Smartlock.KEYCHAIN_GROUP_NAME)")
    }
    
    private func sendSuccess(callbackId: String) {
        let result = CDVPluginResult.init(status: CDVCommandStatus.ok)
        self.commandDelegate.send(result, callbackId: callbackId)
    }
    
    private func sendSuccess(data: [String:Any], callbackId: String) {
        let result = CDVPluginResult.init(status: CDVCommandStatus.ok, messageAs: data)
        self.commandDelegate.send(result, callbackId: callbackId)
    }
    
    private func sendError(error: PluginError, message: String, callbackId: String) {
        let error = CDVPluginResult.init(status: CDVCommandStatus.error, messageAs: [
            "code": error.rawValue,
            "message": message
        ])
        self.commandDelegate.send(error, callbackId: callbackId)
    }
    
}
