
// dev server
config.devServer = {
    ...config.devServer || {},
    port: 3001,
    watchOptions: {
        "aggregateTimeout": 3000,
        "poll": 1000
    },
    open: false,
    historyApiFallback: true
};