import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _api_url = 'http://localhost:8090/api';
  private _auth_url = 'http://localhost:8090/auth';
  private _user_url = 'http://localhost:8090/user';
  private _registration_request_url = 'http://localhost:8090/registration_request';
  private _adventure_url = 'http://localhost:8090/adventures';
  private _user_complaint_url = 'http://localhost:8090/user_complaint';
  private _cottage_url = 'http://localhost:8090/cottages';
  private _boat_url = 'http://localhost:8090/boats';
  private _client_url = 'http://localhost:8090/client';
  private _cottages_url = 'http://localhost:8090/cottages';
  private _entity_url = 'http://localhost:8090/entities';
  private _user_delete_request_url = 'http://localhost:8090/user_delete_request';
  private _reservation_url = 'http://localhost:8090/reservations';
  private _owner_url = 'http://localhost:8090/owner';
  private _clients_review_url = 'http://localhost:8090/clients_review';
  private _owners_review_url = 'http://localhost:8090/owners_review';

  private _refresh_token_url = this._auth_url + '/refresh';

  get owners_review_url(): string{
    return this._owners_review_url;
  }

  get clients_review_url(): string{
    return this._clients_review_url;
  }

  get user_complaint_url(): string{
    return this._user_complaint_url;
  }
  
  get owner_url() : string {
    return this._owner_url;
  }

  get auth_url(): string {
    return this._auth_url;
  }

  get user_url(): string{
    return this._user_url;
  }
  

  get reservation_url(): string{
    return this._reservation_url;
  }

  get user_delete_request_url(): string{
    return this._user_delete_request_url;
  }

  get api_url(): string{
    return this._api_url;
  }

  get adventure_url(): string{
    return this._adventure_url;
  }

  get cottage_url(): string{
    return this._cottage_url;
  }

  get boat_url(): string{
    return this._boat_url;
  }

  get client_url(): string{
    return this._client_url;
  }

  get registration_request_url(): string{
    return this._registration_request_url;
  }

  get refresh_token_url(): string {
    return this._refresh_token_url;
  }

  get entity_url(): string {
    return this._entity_url;
  }

  private _login_url = this._auth_url + '/login';

  get login_url(): string {
    return this._login_url;
  }

  private _change_password_url = this._auth_url + '/change-password';

  get change_password_url(): string {
    return this._change_password_url;
  }

  private _whoami_url = this._api_url + '/whoami';

  get whoami_url(): string {
    return this._whoami_url;
  }

  private _users_url = this._api_url + '/user/all';

  get users_url(): string {
    return this._users_url;
  }

  get cottages_url(): string{
    return this._cottages_url;
  }

  private _foo_url = this._api_url + '/foo';

  get foo_url(): string {
    return this._foo_url;
  }

  private _signup_url = this._auth_url + '/signup';

  get signup_url(): string {
    return this._signup_url;
  }
}
