<template>
    <v-content>
        <v-container fluid grid-list-md text-xs-center>
            <h1>Dashboard</h1>
            <br />
            <Chart />
        </v-container>
    </v-content>
</template>

<script>
import Chart from '@/js/components/Chart'; 
import Card from '@/js/components/Card';

export default {
    name: "Dashboard",
    components: {
        Chart
    },
    beforeCreate: function() {
        if (!this.$session.exists()) {
            this.$router.push('/login');
        }
    }
};
</script>