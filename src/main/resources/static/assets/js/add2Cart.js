// Giỏ hàng ban đầu (mảng trống)
let cart = [];

// Hàm để thêm sản phẩm vào giỏ
function addToCart(productId, productName, productPrice) {
    // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
    let productInCart = cart.find(product => product.id === productId);

    if (productInCart) {
        // Nếu sản phẩm đã có trong giỏ, tăng số lượng lên
        productInCart.quantity++;
    } else {
        // Nếu sản phẩm chưa có trong giỏ, thêm mới vào giỏ
        cart.push({
            id: productId,
            name: productName,
            price: productPrice,
            quantity: 1
        });
    }

    // Cập nhật lại giỏ hàng (hiển thị số lượng và tổng tiền)
    updateCart();
}

// Hàm cập nhật giỏ hàng và tổng tiền
function updateCart() {
    const cartDiv = document.getElementById('mini_cart');
    const totalDiv = document.getElementById('total_price');

    // Làm sạch nội dung giỏ hàng hiện tại
    cartDiv.innerHTML = '';

    if (cart.length === 0) {
        cartDiv.innerHTML = '<p>Giỏ hàng rỗng</p>';
    } else {
        let totalPrice = 0;
        cart.forEach(product => {
            cartDiv.innerHTML += `<p>${product.name} x ${product.quantity} - ${product.price * product.quantity} VND</p>`;
            totalPrice += product.price * product.quantity;
        });

        totalDiv.innerText = totalPrice.toFixed(2);
    }
}

// Lắng nghe sự kiện khi người dùng nhấn nút "Add to cart"
document.querySelectorAll('.add-to-cart').forEach(button => {
    button.addEventListener('click', function(event) {
        event.preventDefault(); // Ngừng hành động mặc định của link

        // Lấy thông tin sản phẩm từ data-* attributes
        const productId = this.getAttribute('data-id');
        const productName = this.getAttribute('data-name');
        const productPrice = parseFloat(this.getAttribute('data-price'));

        // Gọi hàm thêm sản phẩm vào giỏ
        addToCart(productId, productName, productPrice);
    });
});
