module.exports = {
    pages: {
        'index': {
            entry: 'src/pages/home/main.js',
            template: 'public/index.html',
            title: 'Home Page',
            chunks: ['chunk-vendors', 'chunk-common', 'index']
        },
        'doctor': {
            entry: 'src/pages/Doctor_prescription/main.js',
            template: 'public/doctor.html',
            title: 'Prescription Page',
            chunks: ['chunk-vendors', 'chunk-common', 'doctor']
        },
        'pharmacist': {
            entry: 'src/pages/pharmacist_prescription/main.js',
            template: 'public/pharmacist.html',
            title: 'Pharmacist Prescription Page',
            chunks: ['chunk-vendors', 'chunk-common', 'pharmacist']
        },
        'admin': {
            entry: 'src/pages/Admin_professionals_management/main.js',
            template: 'public/admin.html',
            title: 'Admin Professionals Management Page',
            chunks: ['chunk-vendors', 'chunk-common', 'admin']
        }
    }
}