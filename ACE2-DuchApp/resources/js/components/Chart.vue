<template>
    <div>
        <v-layout row wrap>
            <v-flex xs3>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Total de duchas</div>
                        <div class="subheading font-weight-light grey--text">{{total_duchas}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs3>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Total de agua utilizada</div>
                        <div class="subheading font-weight-light grey--text">{{total_agua}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs3>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Promedio de agua por ducha</div>
                        <div class="subheading font-weight-light grey--text">{{promedio_agua}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs3>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Menor Tiempo</div>
                        <div class="subheading font-weight-light grey--text">{{menor_tiempo}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
        <v-layout row wrap>
            <v-flex xs4>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Tiempo promedio</div>
                        <div class="subheading font-weight-light grey--text">{{promedio_tiempo}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs4>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Dinero ahorrado</div>
                        <div class="subheading font-weight-light grey--text">{{dinero}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs4>
                <v-card>
                    <v-icon size="40" >dashboard</v-icon>
                    <v-card-text class="pt-0">
                        <div class="title font-weight-light mb-2">Promedio Dinero ahorrado</div>
                        <div class="subheading font-weight-light grey--text">{{promedio_dinero}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
        <v-layout row wrap>
        <v-flex xs6>
            <v-card class="mt-3 mx-auto">
                <apexchart
                    type="line"
                    :options="options_cantidad_agua"
                    :series="series_cantidad_agua"
                ></apexchart>
                <v-card-text class="pt-0">
                    <div class="title font-weight-light mb-2">Cantidad de Agua</div>
                    <div class="subheading font-weight-light grey--text">Ultimas 7 duchas</div>
                </v-card-text>
            </v-card>
        </v-flex>
        <v-flex xs6>
            <v-card class="mt-3 mx-auto">
                <apexchart type="bar" :options="options_cantidad_agua" :series="series_tiempo"></apexchart>
                <v-card-text class="pt-0">
                    <div class="title font-weight-light mb-2">Tiempos de duchas</div>
                    <div class="subheading font-weight-light grey--text">Ultimas 7 duchas</div>
                </v-card-text>
            </v-card>
        </v-flex>

        <v-flex>
            <v-card class="mt-3 mx-auto" width="500">
                <apexchart type="line" :options="options_cantidad_agua" :series="series_dinero"></apexchart>
                <v-card-text class="pt-0">
                    <div class="title font-weight-light mb-2">Dinero Ahorrado</div>
                    <div class="subheading font-weight-light grey--text">Ultimas 7 duchas</div>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
    </div>
</template>

<script>
import { format } from "timeago.js";

export default {
    data: () => ({
        options_cantidad_agua: {},
        series_cantidad_agua: [],
        series_tiempo: [],
        series_dinero: [],
        total_duchas: 0,
        total_agua: 0,
        promedio_agua: 0,
        promedio_tiempo: 0,
        menor_tiempo: 0,
        dinero: 0,
        promedio_dinero: 0
    }),

    beforeCreate: function() {
        const URL_LAST =
            "https://duch-app-ace2.herokuapp.com/api/duchas/ultimas?email=" +
            this.$session.get("email");
        
        const URL =
            "https://duch-app-ace2.herokuapp.com/api/duchas/datos?email=" +
            this.$session.get("email");

        this.$http
            .get(URL_LAST)
            .then(response => {
                if (response.status === 200) {
                    const duchas = response.data.duchas;

                    const options_agua = duchas.map(d => {
                        return format(d.created_at, "en_US");
                    });

                    const series_agua = response.data.duchas.map(d => {
                        return d.cantidad_agua;
                    });

                    const series_tiempo = response.data.duchas.map(d => {
                        return d.tiempo;
                    });

                    const series_dinero = response.data.duchas.map(d => {
                        return d.dinero_ahorrado;
                    });

                    console.log(series_agua);

                    this.options_cantidad_agua = {
                        chart: {
                            id: "vuechart-example"
                        },
                        xaxis: {
                            categories: options_agua
                        }
                    };
                    this.series_cantidad_agua = [
                        {
                            name: "series-1",
                            data: series_agua
                        }
                    ];
                    this.series_tiempo = [
                        {
                            name: "series-1",
                            data: series_tiempo
                        }
                    ];
                    this.series_dinero = [
                        {
                            name: "series-1",
                            data: series_dinero
                        }
                    ];
                }
            })
            .catch(err => {
                console.log(err);
            });

        this.$http
            .get(URL)
            .then(response => {
                if (response.status === 200) {
                    const datos = response.data[0]
                    this.total_duchas = datos.Total_Duchas;
                    this.total_agua = datos.Total_Agua;
                    this.promedio_agua = datos.Promedio_Agua;
                    this.promedio_tiempo = datos.Menor_Tiempo;
                    this.menor_tiempo = datos.Promedio_Tiempo;
                    this.dinero = datos.Total_Dinero_Ahorrado;
                    this.promedio_dinero = datos.Promedio_Dinero_Ahorrado;
                }
            })
            .catch(errr => {
                console.log(err);
            });
    }
};
</script>