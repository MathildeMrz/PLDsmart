module.exports = {
  pages: {
    'index': {
      entry: 'src/pages/home/main.js',
      template: 'public/index.html',
      title: 'Home Page',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    },
    'prescription': {
      entry: 'src/pages/prescription/main.js',
      template: 'public/prescription.html',
      title: 'Prescription Page',
      chunks: ['chunk-vendors', 'chunk-common', 'prescription']
    }
  }
}
