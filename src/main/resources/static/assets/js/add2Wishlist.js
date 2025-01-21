// Mảng lưu trữ sản phẩm Wishlist
let wishlist = [];

// Hàm hiển thị sản phẩm trong Wishlist
function renderWishlist() {
    const wishlistContent = document.getElementById('wishlist-content');
    wishlistContent.innerHTML = '';

    if (wishlist.length === 0) {
        wishlistContent.innerHTML = `
            <tr id="empty-message">
                <td colspan="5" style="text-align: center;">Your Wishlist is empty.</td>
            </tr>
        `;
        return;
    }

    wishlist.forEach((product, index) => {
        wishlistContent.innerHTML += `
            <tr>
                <td class="product_remove">
                    <a href="#" onclick="removeFromWishlist(${index})">X</a>
                </td>
                <td class="product_thumb">
                    <a href="#"><img src="${product.image}" alt="${product.name}"></a>
                </td>
                <td class="product_name">${product.name}</td>
                <td class="product-price">£${product.price.toFixed(2)}</td>
                <td class="product_quantity">${product.stock}</td>
            </tr>
        `;
    });
}

// Hàm thêm sản phẩm vào Wishlist
function addToWishlist(product) {
    wishlist.push(product);
    renderWishlist();
    alert(`${product.name} has been added to your Wishlist!`);
}

// Hàm xóa sản phẩm khỏi Wishlist
function removeFromWishlist(index) {
    wishlist.splice(index, 1);
    renderWishlist();
}

// Lắng nghe sự kiện khi người dùng nhấn vào "Add to Wishlist"
document.querySelectorAll('.add-to-wishlist').forEach(button => {
    button.addEventListener('click', function(e) {
        e.preventDefault();

        const product = {
            name: this.getAttribute('data-name'),
            image: this.getAttribute('data-img'),
            price: parseFloat(this.getAttribute('data-price')),
            stock: 'In Stock'
        };

        addToWishlist(product);
    });
});

// Hiển thị danh sách sản phẩm khi trang được tải
document.addEventListener('DOMContentLoaded', () => {
    renderWishlist();
});
