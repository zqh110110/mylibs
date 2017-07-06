var path = require('path');
module.exports = {
    entry: "./es6/main.js", //入口文件
    output: { //打包输出的文件
        path: __dirname,
        filename: "bundle.js"
    },
    module: {
        loaders: [{
            test: path.join(__dirname, 'es6'),
            loader: 'babel-loader',
            query: {
                presets: ['es2015']
            }
        }]
    },
    resolve: { // 现在你require文件的时候可以直接使用require('file')，不用使用require('file.coffee')
        extensions: ['.js', '.json', '.coffee']
    }
}