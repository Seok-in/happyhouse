<template>
  <v-container id="dashboard-view" fluid tag="section">
    <v-row>
      <v-col cols="12" md="12">
        <material-card color="orange" icon-fit>
          <template #heading>
            <div class="pa-8 white--text">
              <div class="text-h4 font-weight-light">공지사항</div>
              <div class="text-caption">Notice Board</div>
            </div>
          </template>
        </material-card>
        <router-view></router-view>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import MaterialCard from "../components/MaterialCard.vue";
// Utilities
export default {
  components: { MaterialCard },
  name: "DashboardView",
};
</script>
