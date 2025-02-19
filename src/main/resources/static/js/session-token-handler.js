(function() {
    const metaToken = document.querySelector('meta[name="X-Session-Token"]');
    if (metaToken && metaToken.content) {
        localStorage.setItem('X-Auth-Token', metaToken.content);
    }

    const originalFetch = window.fetch;
    window.fetch = function(url, options) {
        options = options || {};
        options.headers = options.headers || {};

        const token = localStorage.getItem('X-Auth-Token');
        if (token) {
            options.headers['X-Auth-Token'] = token;
        }

        return originalFetch(url, options);
    }

    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function(e) {
                const token = localStorage.getItem('X-Auth-Token');
                if (token && !this.querySelector('input[name="X-Auth-Token"]')) {
                    const tokenInput = document.createElement('input');
                    tokenInput.type = 'hidden';
                    tokenInput.name = 'X-Auth-Token';
                    tokenInput.value = token;
                    this.appendChild(tokenInput);
                }
            });
        });
    });
})();