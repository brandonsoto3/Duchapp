<template>
    <div>
        <!-- Drawer -->
        <v-navigation-drawer
            app
            dark
            floating
            persistent
            mobile-break-point="991"
            width="260"
            v-model="drawer"
            disable-resize-watcher>
            <!-- Title -->
            <v-toolbar flat>
                <v-list>
                    <v-list-tile>
                        <v-list-tile-title class="title">Menú</v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-toolbar>
            <!-- End Title -->
            <v-divider></v-divider>
            <!-- Items -->
            <v-list dense class="pt-0">
                <v-list-tile
                    v-for="(item, i) in menuItems"
                    :key="i"
                    :to="{ name: item.title }"
                    class="v-list-item">
                    <v-list-tile-action>
                        <v-icon>{{ item.icon }}</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                        {{ item.title }}
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
            <!-- End Items -->
        </v-navigation-drawer>
        <!-- End Drawer -->
        <!-- Toolbar -->
        <v-toolbar
            flat
            prominent
            style="background: #eee;">
            <span class="hidden-sm-and-up">
                <v-toolbar-side-icon @click="drawer = !drawer">
                </v-toolbar-side-icon>
            </span>            
            <v-spacer class="hidden-md-and-up"></v-spacer>
            <!-- Title -->
            <v-toolbar-title class="headline text-uppercase">
                <router-link to="/" tag="span" style="cursor: pointer">
                {{ appTitle }}
                </router-link>        
            </v-toolbar-title>
            <!-- End Title -->
            <v-spacer></v-spacer>
            <!-- Items -->
            <v-toolbar-items class="hidden-xs-only">
                <v-btn
                flat
                v-for="item in menuItems"
                :key="item.title"
                :to='{ name: item.title }'>
                    <v-icon left dark>{{ item.icon }}</v-icon>
                    {{ item.title }}
                </v-btn>
                <v-btn
                    flat
                    v-if="session"
                    @click="logout">
                    <v-icon left dark>exit_to_app</v-icon>
                    Salir
                </v-btn>
            </v-toolbar-items>
            <!-- Items -->
        </v-toolbar>
        <!-- End Toolbar -->
    </div>
</template>

<script>
export default {
    name: "NavBar",
    data() {
        return {
            drawer: false,
            menuItems: Array,
            session: false
        };
    },
    props: {
        appTitle: String
    },
    created: function() {
        if (!this.$session.exists()) {
            this.menuItems = [
                { title: 'Home', icon: 'home' },
                { title: 'Registro', icon: 'person_add'},
                { title: 'Login', icon: 'input' }
            ];
            this.session = false;
        } else {
            this.menuItems = [
                { title: 'Home', icon: 'home' },
                { title: 'Dashboard', icon: 'dashboard'},
                { title: 'Top Tiempos', icon: 'timer'},
                { title: 'Top Agua Ahorrada', icon: 'opacity'},
                { title: 'Mis Duchas', icon: 'hot_tub'}
            ];
            this.session = true;
        }
    },
  methods: {
    logout: function () {
        this.$session.destroy()
        this.$router.push('/login')
    }
  }
};
</script>