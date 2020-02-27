export interface SmartlockPlugin {
  readonly SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND;
  readonly SMARTLOCK__REQUEST__DIALOG_CANCELLED;
  readonly SMARTLOCK__SAVE;
  readonly SMARTLOCK__SAVE__BAD_REQUEST;
  readonly SMARTLOCK__DELETE;
  readonly SMARTLOCK__DISABLE_AUTO_SIGN_IN;
  readonly SMARTLOCK__COMMON__UNKOWN;
  readonly SMARTLOCK__COMMON__CONCURRENT_NOT_ALLOWED;
  readonly SMARTLOCK__COMMON__GOOGLE_API_UNAVAILABLE;
  readonly SMARTLOCK__COMMON__RESOLUTION_PROMPT_FAIL;

  /**
   * @description      Request a Credential.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   Credential: if succesfully retrieved.
   * @errors
   *                   SMARTLOCK__REQUEST__ACCOUNTS_NOT_FOUND: if there is no credential saved
   *                   SMARTLOCK__REQUEST__DIALOG_CANCELLED: if user didnt select credential on modal
   *                   SMARTLOCK__COMMON*
   */
  request(): Promise <Credential> ;

  /**
   * @description      Saves a Credential.
   *                   On Android, it may prompt a modal for user authorization.
   * @param
   *                   id: Credential identifier.
   *                   name: User name that will be showm on Request.
   *                   password: credential password.
   *                   profileUri: profile image that will be shown on Request.
   * @returns
   *                   true: if succesfully saved.
   * @errors
   *                   SMARTLOCK__SAVE: if User denied authorization
   *                   SMARTLOCK__SAVE__BAD_REQUEST: if password is empty
   *                   SMARTLOCK__COMMON*
   */
  save(credential: Credential): Promise <true> ;

  /**
   * @description      Deletes a Credential.
   * @param
   *                   id: Credential identifier.
   * @returns
   *                   true: if succesfully deleted.
   * @errors
   *                   SMARTLOCK__DELETE: Couldnt find Credential
   *                   SMARTLOCK__COMMON*
   */
  delete(credentialDeleteRequest: CredentialDeleteRequest): Promise <true> ;

  /**
   * @description      Only on Android, disables auto sign-in for the calling app on the current
   *                   device only, until a successful call to save is subsequently made.
   * @returns
   *                   true: if succesfully executed.
   * @errors
   *                   SMARTLOCK__DISABLE_AUTO_SIGN_IN
   *                   SMARTLOCK__COMMON*
   */
  disableAutoSignIn(): Promise <true> ;
}


export interface Credential {
  id: string;
  name: string;
  password: string;
  profileUri?: string;
}

export interface CredentialDeleteRequest {
  id: string;
}
