function validate() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password-field").value;

    if (username === "" || password === "") {
        swal({title: "", text: "Vui lòng nhập đầy đủ thông tin", icon: "error", close: true, button: "OK"});
        return false;
    }

    // Gửi yêu cầu đăng nhập đến server
    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;  // Nếu Spring Security trả về 302, chuyển hướng
            } else if (response.status === 401) {
                swal({title: "", text: "Sai thông tin đăng nhập", icon: "error", close: true, button: "OK"});
            } else {
                return response.text();
            }
        })
        .then(data => {
            if (data && data.includes("Bad credentials")) {
                swal({title: "", text: "Sai thông tin đăng nhập", icon: "error", close: true, button: "OK"});
            }
        })
        .catch(error => console.error("Lỗi đăng nhập:", error));

    return false; // Ngăn chặn form tự động submit
}


function RegexEmail(inputId) {
    var email = document.getElementById(inputId).value;
    var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if (!emailRegex.test(email)) {
        swal({title: "", text: "Email không hợp lệ", icon: "error", close: true, button: "OK"});
    } else {
        swal({title: "", text: "Email hợp lệ", icon: "success", close: true, button: "OK"});
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('loginForm');
    form.addEventListener('submit', function (event) {
        event.preventDefault();  // Ngăn submit mặc định

        // Giả sử hàm validate() trả về true nếu dữ liệu hợp lệ
        if (validate()) {
            // Nếu cần xử lý gì thêm trước khi submit, có thể thực hiện ở đây
            form.submit();  // Submit form sau khi validate thành công
        }
        // Nếu validate() trả về false thì form không được submit
    });
});





