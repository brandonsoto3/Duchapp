<template>
    <v-container bg grid-list-md text-xs-center>
        <v-layout row wrap align-center>
            <v-flex xs12 mt-5>
                <v-card>
                    <v-card-title
                        class="red accent-3 darken-3 white--text subheading"
                        primary-title
                    >
                        <span>Oops!</span>
                    </v-card-title>
                    <v-card-text>
                        <h1>404</h1>
                        <v-icon x-large>sentiment_very_dissatisfied</v-icon>
                        <h2>Error - página no encontrada</h2>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat color="blue lighten-1" class="white--text" to="/">
                            Página de inicio
                            <v-icon right dark>home</v-icon>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>