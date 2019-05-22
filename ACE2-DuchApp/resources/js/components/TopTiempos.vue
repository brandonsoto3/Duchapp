<template>
    <v-data-table :headers="headers" :items="items" class="elevation-1">
        <template v-slot:items="props">
            <td class="text-xs-right">{{ props.item.Nombre }}</td>
            <td class="text-xs-right">{{ props.item.Tiempo }}</td>
        </template>
    </v-data-table>
</template>

<script>
export default {
    name: "top_tiempos",
    data() {
        return {
            headers: [
                { text: "Nombre", value: "Nombre" },
                { text: "Tiempo (seg)", value: "Tiempo" }
            ],
            items: []
        };
    },

    beforeCreate: function() {
        const URL = "https://duch-app-ace2.herokuapp.com/api/top-tiempos";
        this.$http
            .get(URL)
            .then(response => {
                if (response.status === 200) {
                    console.log(response.data);
                    this.items = response.data
                }
            })
            .catch(err => {
                console.log(err);
            });
    }
};
</script>