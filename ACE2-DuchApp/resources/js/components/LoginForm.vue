<template>
    <div>
        <v-alert
            v-if="error"
            :value="true"
            color="error"
            icon="warning"
            outline
            v-text="mensaje_errores"
        />
        <v-card class="elevation-12">
            <v-toolbar dark color="primary">
                <v-toolbar-title>Iniciar sesión</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <v-form @submit.prevent="login" method="post">
                    <v-text-field
                        prepend-icon="email"
                        id="email"
                        name="login"
                        label="Email"
                        type="email"
                        v-model="email"
                        required
                        :error-messages="emailErrors"
                        @input="$v.email.$touch()"
                        @blur="$v.email.$touch()"
                    ></v-text-field>
                    <v-text-field
                        prepend-icon="lock"
                        id="password"
                        name="password"
                        label="Password"
                        type="password"
                        v-model="password"
                        required
                        :error-messages="passwordErrors"
                        @input="$v.password.$touch()"
                        @blur="$v.password.$touch()"
                    ></v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" type="submit" @click="submit">Login</v-btn>
                <v-btn color="green lighten-5" @click="clear">Limpiar</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, maxLength, email } from "vuelidate/lib/validators";

export default {
    name: "LoginForm",

    mixins: [validationMixin],

    validations: {
        email: { required, email },
        password: { required }
    },

    data: () => ({
        email: "",
        password: "",
        error: false,
        mensaje_errores: ""
    }),

    computed: {
        passwordErrors() {
            const errors = [];
            if (!this.$v.password.$dirty) return errors;
            !this.$v.password.required && errors.push("Campo obligatorio.");
            return errors;
        },
        emailErrors() {
            const errors = [];
            if (!this.$v.email.$dirty) return errors;
            !this.$v.email.email &&
                errors.push("Debe ser un correo electrónico válido.");
            !this.$v.email.required && errors.push("Campo obligatorio.");
            return errors;
        }
    },

    methods: {
        submit() {
            this.$v.$touch();
            if (this.$v.$invalid) {
                return;
            }
            const URL = "https://duch-app-ace2.herokuapp.com/api/auth/login";

            this.$http
                .post(URL, {
                    email: this.email,
                    password: this.password
                })
                .then(response => {
                    if (response.status === 200) {                        
                        this.error = false;                        
                        this.$session.start();
                        this.$session.set('email', this.email);
                        this.$router.push('/dashboard');
                        this.clear();
                    }                    
                }).catch(response => {
                    const errores = response.data.errores;
                    if (errores) {
                        this.mensaje_errores = "Oops!! ";
                        for (const e in errores) {
                            this.mensaje_errores += `${errores[e][0]}\n`;
                        }
                    } else {
                        this.mensaje_errores = "Se produjo un error, no se puede iniciar sesión con estas credenciales.";
                    }
                    this.password = "";
                    this.error = true;
                });
        },
        clear() {
            this.$v.$reset();
            this.email = "";
            this.password = "";
            this.error = false;
        }
    }
};
</script>