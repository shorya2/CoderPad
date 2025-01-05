import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'https://accounts.google.com',
  redirectUri: window.location.origin + '/login/oauth2/code/google',
  clientId: '443294057513-ath4g99vo4gabfv4im3f2nfu3mjq4m2v.apps.googleusercontent.com',
  scope: 'openid profile email',
  strictDiscoveryDocumentValidation: false,
};

@Injectable({
  providedIn: 'root'
})
export class Oauth2Service {
  constructor(private oauthService: OAuthService) {
    this.configure();
  }

  private configure() {
    this.oauthService.configure(authConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  public login() {
    this.oauthService.initLoginFlow();
  }

  public logout() {
    this.oauthService.logOut();
  }

  public get identityClaims() {
    return this.oauthService.getIdentityClaims();
  }

  public get accessToken() {
    return this.oauthService.getAccessToken();
  }
}
