<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateShowersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('duchas', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->integer('cantidad_agua');
            $table->double('tiempo');
            $table->double('dinero_ahorrado');
            $table->integer('numero_cancion');
            $table->timestamps();
            $table->bigInteger('usuario_id')->unsigned();            
        });

        Schema::table('duchas', function($table) {
            $table->foreign('usuario_id')
                ->references('id')
                ->on('usuarios')
                ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropForeign('duchas_usuario_id_foreign');
        Schema::dropIfExists('duchas');
    }
}
