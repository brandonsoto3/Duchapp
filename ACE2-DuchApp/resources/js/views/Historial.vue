<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12>  
                    <TablaHistorial />
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import TablaHistorial from '@/js/components/TablaHistorial';

export default {
    name: "Historial",
    components: {
        TablaHistorial
    },
    beforeCreate: function() {
        if (!this.$session.exists()) {
            this.$router.push('/login');
        }
    }
};
</script>