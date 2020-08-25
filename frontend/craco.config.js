const CracoLessPlugin = require('craco-less')

module.exports = {
  plugins: [
    {
      plugin: CracoLessPlugin,
      options: {
        lessLoaderOptions: {
          lessOptions: {
            modifyVars: {
              '@primary-color': '#002171',
              '@text-color': '#65666d',
              '@text-color-secondary': '#9e9d9d'
            },
            javascriptEnabled: true
          }
        }
      }
    }
  ]
}
