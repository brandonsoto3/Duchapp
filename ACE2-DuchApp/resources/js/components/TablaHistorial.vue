<template>
    <v-data-table :headers="headers" :items="items" class="elevation-1">
        <template v-slot:items="props">
            <td>{{ props.index + 1 }}</td>
            <td class="text-xs-right">{{ props.item.cantidad_agua }}</td>
            <td class="text-xs-right">{{ props.item.tiempo }}</td>
            <td class="text-xs-right">{{ props.item.dinero_ahorrado }}</td>
            <td class="text-xs-right">{{ props.item.numero_cancion }}</td>
            <td class="text-xs-right">
                <timeago :datetime="props.item.created_at " :auto-update="60"></timeago>
            </td>
        </template>
    </v-data-table>
</template>

<script>

export default {
    name: "tabla_historial",
    data() {
        return {
            headers: [
                { text: "No." },
                { text: "Cantidad Agua (lt)", value: "cantidad_agua" },
                { text: "Tiempo (seg)", value: "tiempo" },
                { text: "Precio (Q)", value: "dinero_ahorrado" },
                { text: "No. Cancion", value: "numero_cancion" },
                { text: "Fecha", value: "created_at" }
            ],
            items: []
        };
    },

    beforeCreate: function() {
        const URL = "https://duch-app-ace2.herokuapp.com/api/duchas?email=" + this.$session.get('email');
        this.$http
            .get(URL)
            .then(response => {
                if (response.status === 200) {
                    console.log(response.data.duchas);
                    this.items = response.data.duchas;
                }
            })
            .catch(err => {
                console.log(err);
            });
    }
};
</script>