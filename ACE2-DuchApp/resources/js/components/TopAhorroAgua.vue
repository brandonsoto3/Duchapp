<template>
    <v-data-table :headers="headers" :items="items" class="elevation-1">
        <template v-slot:items="props">
            <td class="text-xs-right">{{ props.item.Nombre }}</td>
            <td class="text-xs-right">{{ props.item.Duchas }}</td>
            <td class="text-xs-right">{{ props.item.Agua }}</td>
        </template>
    </v-data-table>
</template>

<script>
export default {
    name: "top_agua_ahorrada",
    data() {
        return {
            headers: [
                { text: "Nombre", value: "Nombre" },
                { text: "No. Duchas", value: "Duchas" },
                { text: "Agua (lt)", value: "Agua" }
            ],
            items: []
        };
    },

    beforeCreate: function() {
        const URL = "https://duch-app-ace2.herokuapp.com/api/top-agua-ahorrada";
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