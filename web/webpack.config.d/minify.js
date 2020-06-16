
// dev server
config.devServer = {
    ...config.devServer || {},
    host: '0.0.0.0',//your ip address
    port: 3001,
    watchOptions: {
        "aggregateTimeout": 3000,
        "poll": 1000
    },
    open: true,
    historyApiFallback: true
};