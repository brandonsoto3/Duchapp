<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8 md6>  
                    <TopAhorroAgua />
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import TopAhorroAgua from '@/js/components/TopAhorroAgua';

export default {
    name: "TopAhorroAguaView",
    components: {
        TopAhorroAgua
    },
    beforeCreate: function() {
        if (!this.$session.exists()) {
            this.$router.push('/login');
        }
    }
};
</script>