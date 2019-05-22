<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('usuarios', 'API\UserController@index')->name('usuarios');
Route::get('usuario/{id}', 'API\UserController@show')->name('usuario')->where('id', '[0-9]+');;
Route::post('auth/registro', 'API\UserController@store')->name('registro');
Route::post('auth/login', 'API\UserController@login')->name('login');
Route::post('auth/logout', 'API\UserController@logout')->name('logout');
Route::post('ducha/crear', 'API\ShowerController@store')->name('crear_ducha');
Route::get('duchas', 'API\ShowerController@index')->name('duchas');
Route::get('duchas/ultimas', 'API\ShowerController@ultimas_duchas')->name('ultimas_duchas');
Route::get('top-agua-ahorrada', 'API\ShowerController@top_agua_ahorrada')->name('top_agua_ahorrada');
Route::get('top-tiempos', 'API\ShowerController@top_tiempos')->name('top_tiempos');
Route::get('duchas/datos', 'API\ShowerController@obtener_datos')->name('datos');
