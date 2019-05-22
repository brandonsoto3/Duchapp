<?php

use Illuminate\Database\Seeder;

class ShowersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        factory(App\Shower::class, 25)->create();
    }
}
