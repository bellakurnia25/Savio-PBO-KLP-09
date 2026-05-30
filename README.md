# Savio-PBO-KLP-09
SAVIO – Smart Personal Finance Tracker
## 1. Apa itu SAVIO?
SAVIO (Smart Personal Finance Tracker) adalah aplikasi keuangan untuk mengelola keuangan pribadi yang dirancang untuk membantu pengguna mengatur pemasukan, pengeluaran, dan tabungan secara lebih teratur. Aplikasi ini ditujukan terutama bagi mahasiswa dan anak muda yang sering mengalami kesulitan dalam mengontrol keuangan sehari-hari.
Aplikasi ini memungkinkan pengguna untuk mencatat pemasukan, pengeluaran, mengatur alokasi dana otomatis, memantau dana darurat, serta melihat laporan keuangan dalam satu sistem terintegrasi.
SAVIO dibuat menggunakan konsep Object Oriented Programming (OOP) sehingga memiliki struktur kode yang modular, rapi, dan mudah dikembangkan.

## 2. Fitur Utama SAVIO
**a. Dashboard**
Fitur Dashboard berfungsi sebagai pusat informasi keuangan pengguna. Pada halaman ini pengguna dapat melihat secara *real-time* menggunakan *JavaFX Property Binding*:
* Saldo aktif
* Dana kebutuhan
* Dana keinginan
* Dana darurat

*Fitur ini dibuat menggunakan:* `DashboardView.java`, `CustomCard.java`, `DataDompet.java`

---

**b. Transaksi**
Fitur Transaksi digunakan untuk:
* Menambahkan pemasukan
* Menambahkan pengeluaran
* Mengedit transaksi
* Menghapus transaksi
* Melihat riwayat arus kas

Sistem transaksi juga terhubung langsung dengan alokasi dana otomatis.
*Fitur ini dibuat menggunakan:* `TransaksiView.java`, `ModelTransaksi.java`

---

**c. Alokasi Dana**
Fitur Alokasi Dana berfungsi untuk membagi pemasukan pengguna secara otomatis menggunakan metode persentase, yang bebas ditentukan oleh pengguna. 
* *Contoh:* 50% kebutuhan, 30% keinginan, 20% tabungan/dana darurat.
* Perhitungan dilakukan secara otomatis melalui fungsi `alokasikanPemasukanOtomatis()` pada file `DataDompet.java`.

---

**d. Dana Darurat**
Fitur Dana Darurat membantu pengguna menyimpan sebagian pemasukan secara otomatis sebagai tabungan keamanan finansial. Dana darurat akan bertambah otomatis setiap pengguna menambahkan pemasukan (*Income*).
* *Fitur ini menggunakan:* `DanaDaruratView.java`, `DataDompet.java`

---

**e. Laporan**
Fitur Laporan digunakan untuk menampilkan ringkasan kondisi keuangan pengguna berdasarkan transaksi yang telah dilakukan. Laporan membantu pengguna memahami:
* Total pemasukan
* Total pengeluaran
* Kondisi saldo saat ini

*Fitur ini dibuat menggunakan:* `LaporanView.java`

---

**f. Profil**
Fitur Profil digunakan untuk:
* Mengubah nama pengguna
* Mengganti *password*
* Melihat informasi akun

Semua perubahan profil akan langsung tersimpan otomatis ke database JSON.
*Fitur ini dibuat menggunakan:* `ProfilView.java`, `DataSesi.java`, `KoneksiJSON.java`


## 3. Sistem Database & Manajemen Data
SAVIO menggunakan sistem penyimpanan data berbasis berkas (**File-Based Storage**) dengan format **JSON (JavaScript Object Notation)** sebagai database lokal yang persisten. 

Cara kerja manajemen data pada aplikasi ini adalah sebagai berikut:
* **Arsitektur Deserialisasi (Load Data):** Saat aplikasi pertama kali diluncurkan, kelas `KoneksiJSON.java` akan membaca berkas fisik `database_savio.json`. Teks string di dalam berkas tersebut kemudian diurai (*parsing*) dan diubah menjadi objek runtime Java ke dalam `DataDompet` agar bisa ditampilkan di layar.
* **Arsitektur Serialisasi (Save Data):** Setiap kali pengguna melakukan operasi manipulasi data—seperti menambah, mengedit, atau menghapus transaksi—aplikasi akan otomatis mengonversi kembali seluruh objek memori menjadi format teks JSON, lalu menguncinya secara permanen ke dalam berkas `database_savio.json`.
* **Keuntungan Pendekatan Ini:** Aplikasi menjadi sangat ringan, bersifat portabel (tidak perlu menginstal server database tambahan seperti MySQL di laptop penguji), serta proses baca-tulis data berjalan sangat cepat secara lokal.




### 4. Struktur Program

```text
SAVIO/
├── java/
│   └── com/
│       └── savio/
│           ├── dao/
│           │   └── TransaksiRepository.java  # Repository penyimpanan dan pengelolaan data transaksi
│           ├── model/
│           │   ├── DataDompet.java           # Pusat data keuangan dan kalkulasi alokasi dana
│           │   ├── DataSesi.java             # Menyimpan data sesi pengguna yang sedang login
│           │   ├── KomponenKeuangan.java     # Abstract class induk komponen keuangan
│           │   ├── Income.java               # Representasi objek pemasukan
│           │   ├── Outcome.java              # Representasi objek pengeluaran
│           │   ├── ModelTransaksi.java       # Model data transaksi (CRUD transaksi)
│           │   └── User.java                 # Model data pengguna
│           ├── scenes/
│           │   ├── DashboardView.java        # Halaman dashboard dan ringkasan keuangan
│           │   ├── TransaksiView.java        # Halaman manajemen transaksi
│           │   ├── AlokasiView.java          # Halaman pengaturan alokasi dana
│           │   ├── DanaDaruratView.java      # Halaman pemantauan dana darurat
│           │   ├── LaporanView.java          # Halaman laporan keuangan
│           │   ├── ProfilView.java           # Halaman profil pengguna
│           │   ├── LoginView.java            # Halaman login
│           │   ├── WelcomeView.java          # Halaman pembuka aplikasi
│           │   └── MainLayout.java           # Layout utama aplikasi
│           ├── components/
│           │   ├── Sidebar.java              # Komponen menu navigasi aplikasi
│           │   ├── CustomCard.java           # Komponen kartu informasi dashboard
│           │   └── LogoSavio.java            # Komponen logo aplikasi
│           ├── utils/
│           │   ├── ColorPalette.java         # Konfigurasi warna aplikasi
│           │   └── KoneksiJSON.java          # Pengelolaan database JSON (load & save)
│           └── MainApp.java                  # Entry point aplikasi JavaFX
├── resources/
│   ├── database_savio.json                   # Database penyimpanan data pengguna dan transaksi
│   └── logo_savio.png                        # Asset logo aplikasi
└── README.md                                 # Dokumentasi proyek
```

## 5. Panduan Menjalankan Program
**a. Clone Repository**
git clone [https://github.com/username/Savio-PBO-KLP-09.git](https://github.com/username/Savio-PBO-KLP-09.git)

**b. Buka Project**
Buka project menggunakan IDE seperti:
* IntelliJ IDEA
* Visual Studio Code
* NetBeans

**c. Tambahkan JavaFX SDK**
Tambahkan JavaFX SDK ke project.
VM Options:
```Bash
--module-path "PATH_FX/lib" --add-modules javafx.controls
```
Contoh:
```Bash
--module-path "C:/javafx-sdk/lib" --add-modules javafx.controls
```

**d. Jalankan Program**
Run file:
`MainApp.java`

## 6. Struktur Kode & Penerapan Pilar OOP
**a. Encapsulation**
Encapsulation diterapkan dengan penggunaan:
* private attribute,
* getter,
* setter.
Contoh pada file:
`ModelTransaksi.java`
```java
private String deskripsi;

public String getDeskripsi() {
    return deskripsi;
}

public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
}
```
Tujuan:
* melindungi data,
* membatasi akses langsung,
* menjaga keamanan objek.

**b. Inheritance**
Inheritance diterapkan pada:
`KomponenKeuangan.java`
yang menjadi parent class dari:
* Income.java
* Outcome.java
Contoh:
``` java
public class Income extends KomponenKeuangan
public class Outcome extends KomponenKeuangan
```
Tujuan:
* mengurangi duplikasi kode,
* mempermudah pengembangan program.

**c. Abstraction**
Abstraction diterapkan menggunakan abstract class:
```java
public abstract class KomponenKeuangan
```
dengan abstract method:
``` java
public abstract String getTipeKomponen();
public abstract double hitungNilaiBersih();
```
Child class wajib mengimplementasikan method tersebut.
**d. Polymorphism**
Polymorphism diterapkan melalui method:
``` java
hitungNilaiBersih()
```
yang memiliki implementasi berbeda pada:
* Income.java
* Outcome.java
Contoh:
Income.java
``` java
@Override
public double hitungNilaiBersih() {
    return getJumlah();
}
```
Outcome.java
``` java
@Override
public double hitungNilaiBersih() {
    return -getJumlah();
}
```
Method yang sama menghasilkan perilaku berbeda tergantung objek yang digunakan.



## 👥 Tim Pengembang (Kelompok 09)
1. Vivien — Project Manager (PM)
2. Randy — Front End Developer
3. Bela — Back End Developer
