<?php

namespace App\Http\Controllers\API;

use App\Shower;
use Validator;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\User;
use Illuminate\Support\Facades\DB;

class ShowerController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
        ], [
            'required' => 'El campo es obligatorio.',
            'email' => 'Debe ser un correo valido.',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'errores' => $validator->errors()
            ], 400);
        }

        $data = $validator->validate();

        $usuario = User::where('email', $data['email'])->first();

        if (!($usuario)) {
            return response()->json([
                'errores' => [
                    'email' => 'Este correo no se encuetra registrado.',
                ]
            ], 400);
        }
        $duchas = DB::table('duchas')->where('usuario_id', $usuario->id)->get();
        return response()->json([
            'duchas' => $duchas
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
            'cantidad_agua' => 'required|integer',
            'tiempo' => 'required|numeric',
            'dinero_ahorrado' => 'required|numeric',
            'numero_cancion' => 'required|integer',
            'email' => 'required|email',
        ], [
            'required' => 'El campo es obligatorio.',
            'integer' => 'El campo debe de ser un numero entero.',
            'numeric' => 'El campo debe de ser un numero.',
            'email' => 'Debe ser un correo valido.',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'errores' => $validator->errors()
            ], 400);
        }

        $data = $validator->validate();

        $usuario = User::where('email', $data['email'])->first();

        if (!($usuario)) {
            return response()->json([
                'errores' => [
                    'email' => 'Este correo no se encuetra registrado.',
                ]
            ], 400);
        }

        $shower = new Shower();
        $shower->cantidad_agua = $data['cantidad_agua'];
        $shower->tiempo = $data['tiempo'];
        $shower->dinero_ahorrado = $data['dinero_ahorrado'];
        $shower->numero_cancion = $data['numero_cancion'];
        $shower->usuario_id = $usuario->id;
        $shower->save();
        return response()->json([
            'mensaje' => 'Ducha guardada',
            'ducha' => $shower
        ], 200);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function ultimas_duchas(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
        ], [
            'required' => 'El campo es obligatorio.',
            'email' => 'Debe ser un correo valido.',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'errores' => $validator->errors()
            ], 400);
        }

        $data = $validator->validate();

        $usuario = User::where('email', $data['email'])->first();

        if (!($usuario)) {
            return response()->json([
                'errores' => [
                    'email' => 'Este correo no se encuetra registrado.',
                ]
            ], 400);
        }

        $duchas = DB::table('duchas')
            ->where('usuario_id', $usuario->id)
            ->orderBy('id', 'desc')
            ->take(7)
            ->get();
        return response()->json([
            'duchas' => $duchas
        ], 200);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function top_agua_ahorrada()
    {
        return \DB::select('CALL obtener_top_agua_ahorrada();');
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function top_tiempos()
    {
        return \DB::select('CALL obtener_top_tiempos();');
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function obtener_datos(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|email',
        ], [
            'required' => 'El campo es obligatorio.',
            'email' => 'Debe ser un correo valido.',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'errores' => $validator->errors()
            ], 400);
        }

        $data = $validator->validate();

        $usuario = User::where('email', $data['email'])->first();

        if (!($usuario)) {
            return response()->json([
                'errores' => [
                    'email' => 'Este correo no se encuetra registrado.',
                ]
            ], 400);
        }

        return \DB::select('CALL obtener_data_duchas_usuario(?);', [$usuario->email]);
    }
}
