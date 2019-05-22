<?php

namespace App\Http\Controllers\API;

use Illuminate\Support\Facades\Auth;
use App\User;
use Validator;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $usuarios = User::all();
        return response()->json([
            'usuarios' => $usuarios
        ], 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'nombre' => 'required|string|min:4',
            'email' => 'required|email|unique:usuarios',
            'password' => 'required|string|min:4|max:50',
        ], [
            'required' => 'El campo es obligatorio.',
            'nombre.min' => 'Tu nombre debe contener por lo menos 4 caracteres.',
            'nombre.string' => 'Tu nombre no debe ser un numero.',
            'email.email' => 'Debe de ser un correo valido',
            'email.unique' => 'El correo que ingresaste ya esta registrado.',
            'password.min' => 'Tu password debe contener por lo  menos 4 caracteres.',
            'password.max' => 'Tu password debe contener como maximo 50 caracteres.',
        ]);
        if ($validator->fails()) {
            return response()->json([
                'errores' => $validator->errors()
            ], 400);
        }
        $data = $validator->validate();
        $user = new User();
        $user->email = $data['email'];
        $user->nombre = $data['nombre'];
        $user->password = bcrypt($data['password']);
        $user->save();
        return response()->json([
            'mensaje' => 'Usuario registrado',
            'usuario' => $user
        ], 200);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $usuario = User::find($id);
        return response()->json([
            'usuario' => $usuario
        ], 200);
    }

    public function login(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
            'password' => 'required|string|min:4|max:50',
        ], [
            'email.email' => 'Debe de ser un correo valido',
            'required' => 'El campo :attribute es obligatorio.',
            'password.min' => 'Tu password debe contener por lo  menos 4 caracteres.',
            'password.max' => 'Tu password debe contener como maximo 50 caracteres.',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'errores' => $validator->errors()
            ], 400);
        }
        
        $credenciales = $request->only('email', 'password');

        if (Auth::validate($credenciales)) {
            return response()->json([
                'mensaje' => 'Usuario logueado exitosamente.'
            ], 200);
        }
        return response()->json([
            'mensaje' => 'Credenciales invalidas.',
        ], 401);
    }

    public function logout() {
        Auth::logout();
        return response()->json([
            'mensaje' => 'Se ha terminado la sesion.',
        ], 200);
    }
}
