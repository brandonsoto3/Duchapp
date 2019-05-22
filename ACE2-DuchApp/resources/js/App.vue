<template>
    <v-app>
        <NavBar appTitle="Duch App" />
        <v-content transition="slide-x-transition">            
            <!-- View -->
            <div id="core-view">
                <v-fade-transition mode="out-in">
                    <router-view :key="$route.fullPath"></router-view>
                </v-fade-transition>
            </div>            
            <!-- End View -->
            <!-- Footer -->
            <v-footer app class="pa-3">
                <v-spacer></v-spacer>
                <div>
                    &copy;{{ new Date().getFullYear() }} — <strong>NeftXx</strong>
                </div>
            </v-footer>
            <!-- End Footer -->
        </v-content>
    </v-app>
</template>

<script>
import NavBar from "@/js/components/NavBar";

export default {
    name: "App",
    components: {
        NavBar
    },
    data() {
        return {
        }
    }
}
</script>

<style>
#core-view {
  padding-bottom: 100px;
}
</style>