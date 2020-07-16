
// dev server
config.devServer = {
    ...config.devServer || {},
    host: 'localhost',//your ip address
    port: 3001,
    watchOptions: {
        "aggregateTimeout": 3000,
        "poll": 1000
    },

    open: true,
    historyApiFallback: true, //publicPath: "/web"

};