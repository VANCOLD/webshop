const profile   = document.getElementById('profile-button');
const login     = document.getElementById('register-button');
const cart      = document.getElementById('cart-button');
const logout    = document.getElementById('logout-button');
const admin     = document.getElementById('admin-button');

const viewProfile   = 'view_profile';   
const viewCart      = 'view_cart';           
const editCart      = 'edit_cart';
const editUsers     = 'edit_users';
const editProducts  = 'edit_products';
  

document.getElementById('logout-button').addEventListener('click', function (e) {
    localStorage.setItem('token','');
});


$(document).ready(function() {

    // Get token
    var token = localStorage.getItem("token");

    if( token == '' || token == null ) {

        if(window.location.href.includes('/register.html')) {
            login.style.display = 'none';
        }

        profile.style.display = 'none';
        cart.style.display = 'none';
        logout.style.display = 'none';
        admin.style.display  = 'none';

    } else {
        const jwtParser = new JWTParser(token);
        
        try {
            const payload = jwtParser.parse();
            const privs   = payload.authority.split(' ');

            if (privs.includes(viewProfile)) {
                profile.style.display = 'visible';
            }

            if(privs.includes(viewCart)) {
                cart.style.display = 'visible';
            }

            if(privs.includes(editCart) || privs.includes(editUsers) || privs.includes(editProducts)) {
                admin.style.display = 'visible';
            } else {
                admin.style.display = 'none'
            }

            login.style.display = 'none';

        } catch (error) {
            console.log('Error parsing JWT:', error.message);
        }
    }

});


class JWTParser {
    constructor(token) {
      this.token = token;
    }
  
    parse() {
        try {
            const parts = this.token.split('.');
            if (parts.length !== 3) {
            throw new Error('Invalid token format');
            }
    
            const payload = JSON.parse(atob(parts[1]));
            return payload;
        } catch (error) {
            throw new Error('Invalid token: ' + error.message);
        }
    }
}