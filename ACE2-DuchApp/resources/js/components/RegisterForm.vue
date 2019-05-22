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
        <v-alert v-if="success" :value="true" color="success" icon="check_circle" outline>
            Registro completado. Tu puedes Logearte ahora:
            <router-link :to="{name:'Login'}">Login</router-link>
        </v-alert>
        <v-card class="elevation-12 mt-3">
            <v-toolbar dark color="primary">
                <v-toolbar-title>Registro</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <v-form @submit.prevent="login" method="post">
                    <v-text-field
                        prepend-icon="person"
                        id="nombre"
                        name="nombre"
                        label="Nombre"
                        type="text"
                        v-model="nombre"
                        required
                        :error-messages="nombreErrors"
                        @input="$v.nombre.$touch()"
                        @blur="$v.nombre.$touch()"
                    ></v-text-field>
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
                    <v-text-field
                        prepend-icon="lock"
                        id="confirm_password"
                        name="confirm_password"
                        label="Confirma Password"
                        type="password"
                        v-model="confirm_password"
                        required
                        :error-messages="confirm_passwordErrors"
                        @input="$v.confirm_password.$touch()"
                        @blur="$v.confirm_password.$touch()"
                    ></v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" type="submit" @click="submit">Registrar</v-btn>
                <v-btn color="green lighten-5" @click="clear">Reiniciar</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import {
    required,
    maxLength,
    email,
    minLength,
    sameAs
} from "vuelidate/lib/validators";

export default {
    name: "LoginForm",

    mixins: [validationMixin],

    validations: {
        nombre: {
            required,
            minLength: minLength(4)
        },
        email: {
            required,
            email
        },
        password: {
            required,
            minLength: minLength(4),
            maxLength: maxLength(50)
        },
        confirm_password: {
            required,
            sameAsPassword: sameAs("password")
        }
    },

    data: () => ({
        nombre: "",
        email: "",
        password: "",
        confirm_password: "",
        error: false,
        success: false,
        mensaje_errores: ""
    }),

    computed: {
        nombreErrors() {
            const errors = [];
            if (!this.$v.nombre.$dirty) return errors;
            !this.$v.nombre.required && errors.push("Campo obligatorio.");
            !this.$v.nombre.minLength &&
                errors.push(
                    "Tu nombre debe contener por lo menos 4 caracteres."
                );
            return errors;
        },

        emailErrors() {
            const errors = [];
            if (!this.$v.email.$dirty) return errors;
            !this.$v.email.email &&
                errors.push("Debe ser un correo electrónico válido.");
            !this.$v.email.required && errors.push("Campo obligatorio.");
            return errors;
        },

        passwordErrors() {
            const errors = [];
            if (!this.$v.password.$dirty) return errors;
            !this.$v.password.required && errors.push("Campo obligatorio.");
            !this.$v.password.minLength &&
                errors.push(
                    "Tu password debe contener por lo  menos 4 caracteres."
                );
            !this.$v.password.maxLength &&
                errors.push(
                    "Tu password debe contener como maximo 50 caracteres."
                );
            return errors;
        },

        confirm_passwordErrors() {
            const errors = [];
            if (!this.$v.confirm_password.$dirty) return errors;
            !this.$v.confirm_password.required &&
                errors.push("Campo obligatorio.");
            !this.$v.confirm_password.sameAsPassword &&
                errors.push("La confirmación de la contraseña no coincide.");
            return errors;
        }
    },

    methods: {
        submit() {
            this.$v.$touch();
            if (this.$v.$invalid) {
                return;
            }
            const URL = "https://duch-app-ace2.herokuapp.com/api/auth/registro";
            this.$http
                .post(URL, {
                    nombre: this.nombre,
                    email: this.email,
                    password: this.password
                })
                .then(response => {
                    if (response.status === 200) {
                        this.error = false;
                        this.success = true;
                        this.clear();
                    }                    
                })
                .catch(response => {
                    const errores = response.data.errores;
                    if (errores) {
                        this.mensaje_errores = "Oops!! ";
                        for (const e in errores) {
                            this.mensaje_errores += `${errores[e][0]}\n`;
                        }
                    } else {
                        this.mensaje_errores +=
                            "Hubo un error, no se pudo completar el registro.";
                    }
                    this.password = "";
                    this.confirm_password = "";
                    this.error = true;
                    this.success = false;
                });
        },
        clear() {
            this.$v.$reset();
            this.nombre = "";
            this.email = "";
            this.password = "";
            this.confirm_password = "";
            this.error = false;
        }
    }
};
</script>