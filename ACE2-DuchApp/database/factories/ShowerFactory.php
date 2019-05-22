<?php

/* @var $factory \Illuminate\Database\Eloquent\Factory */

use App\Shower;
use Faker\Generator as Faker;

$factory->define(Shower::class, function (Faker $faker) {
    $cantidad_agua = rand(50, 200);
    $dinero_ahorrado = $cantidad_agua * 0.5;

    return [
        'cantidad_agua' => $cantidad_agua,
        'tiempo' => rand(60, 200),
        'dinero_ahorrado' => $dinero_ahorrado,
        'numero_cancion' => rand(1, 3),
        'usuario_id' => 1
    ];
});
