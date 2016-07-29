module.exports = function(grunt){

   grunt.initConfig({
      babel: {
         amd: {
            options:{
               presets: ['es2015'],
               plugins: ["transform-es2015-modules-amd"]
            },
            files:[{
              expand: true,
              cwd: 'src',
              src: ['*.js'],
              dest: 'amd/',
              ext: '.js'
            }]
         },
         // cmd:{
         //    options:{
         //       presets: ['es2015'],
         //       plugins: ["transform-es2015-modules-commonjs"]
         //    },
         //    files:[{
         //      expand: true,
         //      cwd: 'src',
         //      src: ['*.js'],
         //      dest: 'cmd/',
         //      ext: '.js'
         //    }]
         // },
         amdTest: {
            options:{
               presets: ['es2015'],
               plugins: ["transform-es2015-modules-amd"]
            },
            files:[{
              expand: true,
              cwd: 'test',
              src: ['*.js'],
              dest: 'amd/',
              ext: '.js'
            }]
         },
      },
      copy: {
         mbm: {
            files:[
               { expand: true, cwd:'amd', src:['**/*.js'], dest: 'D:/javascript/xdata-mbm/js/dataviz/'}
            ]
         },
         main: {
            files:[
               {src:['amd/**/*.js', 'cmd/**/*.js'], dest: '../../project/jobs/jobs-kdd/jobs-kdd-dataviz/src/main/resources/'}
            ]
         }
      },
      watch: {
         scripts:{
            files: ['src/**/*.js', 'test/**/*.js'],
            tasks: ['babel']
         },
         amd: {
            files: 'amd/**/*.js',
            tasks: ['copy']
         }
      }
   });

   grunt.loadNpmTasks('grunt-contrib-watch');
   grunt.loadNpmTasks('grunt-contrib-copy');
   grunt.loadNpmTasks('grunt-contrib-concat');
   grunt.loadNpmTasks('grunt-babel');
   grunt.registerTask('default', ['babel']);
   grunt.registerTask('autocompile', ['watch']);
   grunt.registerTask('copy2mbm', ['babel', 'copy:mbm']);
   grunt.registerTask('copy2server', ['copy:main']);
}
