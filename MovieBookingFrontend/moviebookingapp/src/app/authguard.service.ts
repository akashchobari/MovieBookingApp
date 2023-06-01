import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthguardService{

  constructor(private authService:AuthService,private router:Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return sessionStorage.getItem('isAuthenticated')=='true'?true:false;
  }
}

export const AuthGuard:CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot):any=>{
  return inject(AuthguardService).canActivate(next,state);
}
