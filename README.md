# Cordova Plugin Android Smartlock

## Platforms

* Android - Minimum SDK 22


## How to use

Cordova

```
window.smartlock.save({
  id,
  name,
  password
}); 
```
```
window.smartlock.request();
```
```
window.smartlock.delete({ id });
```

Ionic-native (Not supported)

[Example](https://github.com/jorgnava/smartlock-plugin-test)


## Install

**NPM**

Add to your package.json

```
devDependencies: {
  ...
  "cordova-plugin-smart-lock": "github:okode/cordova-plugin-smart-lock#{{VERSION}}",
  ...
}

```


## Plugin Errors

### Request

- **SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND** = `-100`
- **SMARTLOCK__REQUEST__DIALOG_CANCELLED** = `-101`
### Save
- **SMARTLOCK__SAVE** = `-200`
- **SMARTLOCK__SAVE__BAD_REQUEST** = `-201`
### Delete
- **SMARTLOCK__DELETE** = `-300`
### Common
- **SMARTLOCK__COMMON__UNKOWN** = `-400`
- **SMARTLOCK__COMMON__CONCURRENT_NOT_ALLOWED** = `-401`
- **SMARTLOCK__COMMON__GOOGLE_API_UNAVAILABLE** = `-402`
- **SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL** = `-403`


## License

The project is MIT licensed: [MIT](https://opensource.org/licenses/MIT).

