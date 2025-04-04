/**
 * GreenBook Admin JavaScript
 */

document.addEventListener('DOMContentLoaded', function() {
    // Toggle sidebar
    const sidebarToggle = document.getElementById('sidebarToggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', function() {
            document.body.classList.toggle('sb-sidenav-toggled');

            // Save sidebar state to localStorage
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

    // Check sidebar state from localStorage
    if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        document.body.classList.add('sb-sidenav-toggled');
    }

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Initialize tooltips
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));

    // Initialize popovers
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
    const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl));

    // Image previews for file inputs
    const imageInputs = document.querySelectorAll('.image-input');
    imageInputs.forEach(function(input) {
        input.addEventListener('change', function(e) {
            const preview = document.getElementById(this.dataset.preview);
            if (preview && this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    });

    // Delete confirmation dialog
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to delete this item? This action cannot be undone.')) {
                e.preventDefault();
            }
        });
    });

    // Toggle status confirmation dialog
    const toggleStatusButtons = document.querySelectorAll('.btn-toggle-status');
    toggleStatusButtons.forEach(function(button) {
        button.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to change the status of this item?')) {
                e.preventDefault();
            }
        });
    });

    // Initialize DataTables
    initializeDataTables();

    // Initialize TinyMCE if available
    initializeTinyMCE();

    // Handle dynamic form fields for promotions
    handlePromotionTypeChange();

    // Format currency inputs
    formatCurrencyInputs();
});

/**
 * Initialize DataTables
 */
function initializeDataTables() {
    if (typeof $.fn.DataTable !== 'undefined') {
        // Books table
        if ($('#booksTable').length) {
            $('#booksTable').DataTable({
                responsive: true,
                order: [[0, 'desc']]
            });
        }

        // Orders table
        if ($('#ordersTable').length) {
            $('#ordersTable').DataTable({
                responsive: true,
                order: [[0, 'desc']]
            });
        }

        // Users table
        if ($('#usersTable').length) {
            $('#usersTable').DataTable({
                responsive: true,
                order: [[0, 'desc']]
            });
        }

        // Categories table
        if ($('#categoriesTable').length) {
            $('#categoriesTable').DataTable({
                responsive: true,
                order: [[0, 'desc']]
            });
        }

        // Promotions table
        if ($('#promotionsTable').length) {
            $('#promotionsTable').DataTable({
                responsive: true,
                order: [[0, 'desc']]
            });
        }

        // Blogs table
        if ($('#blogsTable').length) {
            $('#blogsTable').DataTable({
                responsive: true,
                order: [[0, 'desc']]
            });
        }
    }
}

/**
 * Initialize TinyMCE
 */
function initializeTinyMCE() {
    if (typeof tinymce !== 'undefined') {
        tinymce.init({
            selector: '.tinymce-editor',
            height: 400,
            menubar: true,
            plugins: [
                'advlist autolink lists link image charmap print preview anchor',
                'searchreplace visualblocks code fullscreen',
                'insertdatetime media table paste code help wordcount'
            ],
            toolbar: 'undo redo | formatselect | ' +
                'bold italic backcolor | alignleft aligncenter ' +
                'alignright alignjustify | bullist numlist outdent indent | ' +
                'removeformat | help',
            content_style: 'body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif; font-size: 16px; }'
        });
    }
}

/**
 * Handle promotion type change
 */
function handlePromotionTypeChange() {
    const promotionTypeSelect = document.getElementById('promotionType');
    const valueLabel = document.getElementById('valueLabel');

    if (promotionTypeSelect && valueLabel) {
        promotionTypeSelect.addEventListener('change', function() {
            if (this.value === 'PERCENTAGE') {
                valueLabel.textContent = 'Discount Percentage (%)';
            } else if (this.value === 'FIXED_AMOUNT') {
                valueLabel.textContent = 'Discount Amount';
            }
        });

        // Trigger change event on page load to set initial state
        const event = new Event('change');
        promotionTypeSelect.dispatchEvent(event);
    }
}

/**
 * Format currency inputs
 */
function formatCurrencyInputs() {
    const currencyInputs = document.querySelectorAll('.currency-input');

    currencyInputs.forEach(function(input) {
        input.addEventListener('input', function() {
            // Remove any non-digit characters except decimal point
            let value = this.value.replace(/[^\d.]/g, '');

            // Format with thousands separators
            if (value) {
                value = parseFloat(value).toLocaleString('vi-VN');
            }

            this.value = value;
        });
    });
}

/**
 * Show loading spinner
 */
function showLoading() {
    const spinner = document.createElement('div');
    spinner.className = 'spinner-overlay';
    spinner.innerHTML = '<div class="spinner-border text-primary" role="status"><span class="visually-hidden">Loading...</span></div>';
    document.body.appendChild(spinner);
}

/**
 * Hide loading spinner
 */
function hideLoading() {
    const spinner = document.querySelector('.spinner-overlay');
    if (spinner) {
        spinner.remove();
    }
}

/**
 * Format currency value
 */
function formatCurrency(value) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
}