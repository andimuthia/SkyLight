# SkyLight
SkyLight adalah aplikasi mobile yang bertujuan untuk memudahkan pengguna dalam mendapatkan informasi lengkap mengenai berbagai maskapai penerbangan. Aplikasi ini menyediakan berbagai fitur utama yang membantu pengguna untuk menemukan, melihat, dan menyimpan data maskapai dengan mudah.

# Fitur Utama SkyLight:
1. Daftar Maskapai
   - Menampilkan daftar lengkap maskapai penerbangan.
   - Informasi yang disajikan termasuk nama maskapai dan detail lainnya.
2. Detail Maskapai
   - Pengguna dapat mengklik maskapai tertentu untuk melihat informasi lebih rinci.
3. Fitur pencarian untuk memudahkan pengguna menemukan maskapai berdasarkan nama maskapai
   - SearchView yang interaktif untuk pengalaman pengguna yang lebih baik.
4. Simpan Favorit
   - Pengguna dapat menyimpan maskapai favorit mereka untuk diakses nanti.
   - Data maskapai yang disimpan tidak dapat disimpan ulang jika sudah ada dalam daftar favorit.
5. Navigasi Intuitif
   - Menggunakan Navigation Component untuk navigasi yang mulus antara berbagai fragment dan activity.
   - Fragment untuk menampilkan daftar maskapai, pencarian maskapai, favorit maskapai dan tampilan profile
6. Penyimpanan Lokal
   - Menyimpan data maskapai favorit secara lokal menggunakan SQLite.

# Cara penggunaan
1. Registrasi
   - Buka Aplikasi SkyLight
   - Setelah aplikasi terbuka, kita akan melihat opsi untuk Register.
   - Isi Formulir Registrasi yaitu unsername dan password.
   - Tekan tombol Register untuk mengirimkan informasi dan membuat akun baru.
2. Login
   - Setelah berhasil registrasi, kita akan diarahkan ke halaman Login.
   - Isi Formulir Login
   - Masukkan username dan password yang telah Anda daftarkan
   - Tekan Tombol Login untuk masuk ke akun Anda.
3. HomeFragment: Menampilkan Semua Daftar Maskapai
   - Beranda Aplikasi
     Setelah login, Anda akan diarahkan ke halaman Home yang menampilkan daftar semua maskapai.
4. Detail Maskapai
   - Memilih Maskapai
     Klik pada salah satu maskapai dalam daftar untuk melihat detail lebih lanjut.
   - Melihat Informasi Detail
   - Halaman detail akan menampilkan informasi rinci seperti nama maskapai, rute penerbangan, tahun didirikan dan website resmi maskapai.
   - Pada halaman detail maskapai, kita akan melihat ikon Bookmark.
   - Klik ikon Bookmark untuk menyimpan maskapai ke daftar favorit.
6. SearchFragment: Pencarian Maskapai
   - Gunakan Fitur Pencarian
     Di halaman search, gunakan fitur Search untuk mencari maskapai berdasarkan nama
   - Hasil Pencarian
     Hasil pencarian akan menampilkan maskapai yang sesuai dengan nama yang dimasukkan.
7. Simpan Maskapai Favorit
   - Mengakses Maskapai Favorit
     Pergi ke bagian Favorit untuk melihat daftar maskapai yang telah Anda simpan.
     Pada bagian ini kita juga bisa menghapus maskapai yang telah kita simpan 
8. Profil Pengguna
   - Melihat Profil
     Klik pada ikon Profil di menu navigasi untuk melihat informasi akun seperti username dan password.
   - Memilih gambar Profil
     Pada bagian ini kita juga bisa memilih gambar dari galeri perangkat untuk digunakan sebagai profil  akun.

