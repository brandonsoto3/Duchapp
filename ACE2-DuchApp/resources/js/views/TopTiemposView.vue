<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8 md6>  
                    <TopTiempos />
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import TopTiempos from '@/js/components/TopTiempos';

export default {
    name: "TopTiemposView",
    components: {
        TopTiempos
    },
    beforeCreate: function() {
        if (!this.$session.exists()) {
            this.$router.push('/login');
        }
    }
};
</script>