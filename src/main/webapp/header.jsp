<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="data.jsp" flush="true"></jsp:include>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Java JSP</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="/home/img/favicon.png" rel="icon">
  <link href="/home/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Barlow:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Roboto:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="/home/css/bootstrap.min.css" rel="stylesheet">
  <link href="/home/css/bootstrap-icons.css" rel="stylesheet">
  <link href="/home/css/swiper-bundle.min.css" rel="stylesheet">
  <link href="/home/css/glightbox.min.css" rel="stylesheet">
  <link href="/home/css/aos.css" rel="stylesheet">

  <!-- Template Main CSS Files -->
  <link href="/home/css/variables.css" rel="stylesheet">
  <link href="/home/css/main.css" rel="stylesheet">

</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    <div class="container-fluid container-xl d-flex align-items-center justify-content-between">

      <a href="/home/" class="logo d-flex align-items-center">
        <!-- Uncomment the line below if you also wish to use an image logo -->
        <!-- <img src="/home/img/logo.png" alt=""> -->
        <h1>ZenBlog</h1>
      </a>

      <nav id="navbar" class="navbar">
        <ul>
          <li><a href="/home/"><i class="bi bi-house-heart-fill fs-4"></i></a></li>
          <li><a href="/home/gioi-thieu/" class="fs-5">Giới thiệu</a></li>
          <li class="dropdown"><a href="/home/tin-tuc/" class="fs-5"><span>Tin tức</span> <i class="bi bi-chevron-down dropdown-indicator"></i></a>
            <ul>
              <li><a href="/home/s">Tìm kiếm</a></li>
              <li><a href="#">Drop Down 1</a></li>
              <li><a href="#">Drop Down 2</a></li>
              <li><a href="#">Drop Down 3</a></li>
              <li><a href="#">Drop Down 4</a></li>
            </ul>
          </li>

          <li><a href="/home/dich-vu/" class="fs-5">Dịch vụ</a></li>
          <li><a href="/home/hinh-anh/" class="fs-5">Hình ảnh</a></li>
          <li><a href="/home/lien-he/" class="fs-5">Liên hệ</a></li>
        </ul>
      </nav><!-- .navbar -->

      <div class="position-relative">
        <a href="#" class="mx-2"><span class="bi-facebook"></span></a>
        <a href="#" class="mx-2"><span class="bi-twitter"></span></a>
        <a href="#" class="mx-2"><span class="bi-instagram"></span></a>

        <a href="#" class="mx-2 js-search-open"><span class="bi-search"></span></a>
        <i class="bi bi-list mobile-nav-toggle"></i>

        <!-- ======= Search Form ======= -->
        <div class="search-form-wrap js-search-form-wrap">
          <form action="/home/tin-tuc/" method="post" class="search-form">
            
            <input type="text" placeholder="Tìm kiếm" class="form-control" name="key">
            <button type="submit" class="btn btn-primary"><span class="icon bi-search"></span></button>
          </form>
        </div><!-- End Search Form -->

      </div>

    </div>

  </header><!-- End Header -->