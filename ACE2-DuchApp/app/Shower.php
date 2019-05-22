<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Shower extends Model
{
        /**
    * The database table used by the model.
    *
    * @var string
    */
    protected $table = 'duchas';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'cantidad_agua',
        'tiempo',
        'dinero_ahorrado',
        'numero_cancion',
        'usuario_id'
    ];

}
