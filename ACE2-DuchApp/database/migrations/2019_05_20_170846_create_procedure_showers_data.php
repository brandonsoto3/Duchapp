<?php

use Illuminate\Database\Migrations\Migration;

class CreateProcedureShowersData extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $procedure = "
            CREATE PROCEDURE obtener_data_duchas_usuario(
                in email varchar(191)
            )
            BEGIN
                SELECT
                    Count(*) Total_Duchas,
                    SUM(cantidad_agua) Total_Agua,
                    AVG(cantidad_agua) Promedio_Agua,
                    MIN(tiempo) Menor_Tiempo,
                    AVG(tiempo) Promedio_Tiempo,
                    SUM(dinero_ahorrado) Total_Dinero_Ahorrado,
                    AVG(dinero_ahorrado) Promedio_Dinero_Ahorrado
                FROM duchas d inner join usuarios u on d.usuario_id = u.id
                WHERE u.email = email;
            END
        ";
        DB::unprepared("DROP PROCEDURE IF EXISTS obtener_data_duchas_usuario;");
        DB::unprepared($procedure);
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        DB::unprepared("DROP PROCEDURE IF EXISTS obtener_data_duchas_usuario;");
    }
}
