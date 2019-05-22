<template>
    <v-content>
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 md5>                    
                    <RegisterForm />
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import RegisterForm from '@/js/components/RegisterForm.vue';

export default {
    components: {
        RegisterForm
    },

    data() {
        return {
            nombre: "",
            email: "",
            password: "",
            error: false,
            errors: {},
            success: false
        };
    },
    beforeCreate: function () {
        if (this.$session.exists()) {
            this.$router.push('/dashboard')
        }
    },
};
</script>