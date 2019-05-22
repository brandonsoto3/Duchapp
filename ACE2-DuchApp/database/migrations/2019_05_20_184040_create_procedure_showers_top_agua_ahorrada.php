<?php

use Illuminate\Database\Migrations\Migration;

class CreateProcedureShowersTopAguaAhorrada extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $procedure = "
            CREATE PROCEDURE obtener_top_agua_ahorrada()
            BEGIN
                SELECT COUNT(*) Duchas, u.nombre Nombre, SUM(d.cantidad_agua) Agua
                FROM usuarios u INNER JOIN duchas d ON u.id = d.usuario_id
                GROUP BY Nombre
                ORDER BY Duchas DESC
                LIMIT 10;
            END
        ";
        DB::unprepared("DROP PROCEDURE IF EXISTS obtener_top_agua_ahorrada;");
        DB::unprepared($procedure);
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        DB::unprepared("DROP PROCEDURE IF EXISTS obtener_top_agua_ahorrada;");
    }
}
