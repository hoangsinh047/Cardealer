let cart = [];

document.querySelectorAll('.add-to-cart').forEach(button => {
    button.addEventListener('click', event => {
        event.preventDefault();

        // Lấy dữ liệu từ thuộc tính data của nút
        const productImg = button.getAttribute('data-img');
        const productId = button.getAttribute('data-id');
        const productName = button.getAttribute('data-name');
        const productPrice = parseFloat(button.getAttribute('data-price'));

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
        const existingItem = cart.find(item => item.id === productId);

        if (existingItem) {
            existingItem.quantity += 1; // Tăng số lượng nếu sản phẩm đã tồn tại
        } else {
            cart.push({
                id: productId, // Gán đúng thuộc tính
                name: productName,
                img: productImg,
                price: productPrice,
                quantity: 1 // Số lượng mặc định là 1
            });
        }

        // Cập nhật giao diện giỏ hàng
        updateMiniCart();
    });
});

function updateMiniCart() {
    const miniCart = document.getElementById('mini_cart');
    const cartSummary = document.getElementById('cart-summary');

    miniCart.innerHTML = '';

    if (cart.length === 0) {
        miniCart.innerHTML = '<p>Giỏ hàng rỗng</p>';
        cartSummary.innerText = '0 items - $0.00';
        return;
    }

    let totalPrice = 0;
    let totalItems = 0;

    cart.forEach(item => {
        totalPrice += item.price * item.quantity;
        totalItems += item.quantity;

        miniCart.innerHTML += `
            <div class="cart_item">
                <div class="cart_img">
                    <img src="${item.img}" alt="${item.name}">
                </div>
                <div class="cart_info">
                    <span>${item.name}</span>
                    <span class="cart_price">$${item.price.toFixed(2)}</span>
                    <span class="quantity">Qty: ${item.quantity}</span>
                </div>
                <div class="cart_remove">
                    <a title="Remove this item" href="#" onclick="removeFromCart('${item.id}')"><i class="fa fa-times-circle"></i></a>
                </div>
            </div>
        `;
    });

    miniCart.innerHTML += `
        <div class="cart_button">
            <a href="/checkout" class="checkout_button">Check Out</a>
        </div>
    `;

    // Cập nhật thông tin tổng cộng
    cartSummary.innerText = `${totalItems} items - $${totalPrice.toFixed(2)}`;
}

function removeFromCart(productId) {
    // Xóa sản phẩm khỏi giỏ hàng dựa trên ID
    cart = cart.filter(item => item.id !== productId);
    updateMiniCart();
}
