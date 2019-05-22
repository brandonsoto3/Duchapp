<?php

use Illuminate\Database\Migrations\Migration;

class CreateProcedureShowersTopTiempos extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $procedure = "
            CREATE PROCEDURE obtener_top_tiempos()
            BEGIN
                SELECT u.nombre Nombre, MIN(d.tiempo) Tiempo
                FROM usuarios u INNER JOIN duchas d ON u.id = d.usuario_id
                GROUP BY Nombre
                ORDER BY Tiempo ASC
                LIMIT 10;
            END
        ";
        DB::unprepared("DROP PROCEDURE IF EXISTS obtener_top_tiempos;");
        DB::unprepared($procedure);
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        DB::unprepared("DROP PROCEDURE IF EXISTS obtener_top_tiempos;");
    }
}
