<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8 md4>                    
                    <LoginForm/>
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import LoginForm from "@/js/components/LoginForm.vue";
export default {
    name: "Login",
    components: {
        LoginForm
    },
    beforeCreate: function () {
        if (this.$session.exists()) {
            this.$router.push('/dashboard')
        }
    },
};
</script>