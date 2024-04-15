<template>
  <div>
      <router-view :key="$route.fullPath"  />
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
*{
    margin: 0;
    padding: 0;
}
</style>
