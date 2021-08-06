
// dev server
config.devServer = {
    ...config.devServer || {},
    host: 'localhost',//your ip address
    port: 3001,


    open: true,
    historyApiFallback: true, //publicPath: "/web"

};