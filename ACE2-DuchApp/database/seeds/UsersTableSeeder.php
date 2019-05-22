<?php

use Illuminate\Support\Str;
use Illuminate\Database\Seeder;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('usuarios')->insert([
            'nombre' => 'Ronald Berduo',
            'email' => 'ronald@gmail.com',
            'password' => bcrypt('12345678'),
            'remember_token' => Str::random(10),
        ]);
        DB::table('usuarios')->insert([
            'nombre' => 'Sergio Leal',
            'email' => 'sergio@gmail.com',
            'password' => bcrypt('12345678'),
            'remember_token' => Str::random(10),
        ]);
        // factory(App\User::class, 100)->create();
    }
}
