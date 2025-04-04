/**
 * GreenBook Main JavaScript File
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    initTooltips();

    // Initialize popovers
    initPopovers();

    // Initialize form validation
    initFormValidation();

    // Auto-dismiss alerts
    initAlertDismissal();

    // Initialize custom file inputs
    initCustomFileInputs();

    // Initialize image previews
    initImagePreviews();

    // Initialize confirmation dialogs
    initConfirmationDialogs();

    // Initialize currency formatting
    initCurrencyFormatting();

    // Handle sidebar toggle on mobile
    initSidebarToggle();
});

/**
 * Initialize Bootstrap tooltips
 */
function initTooltips() {
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    if (tooltipTriggerList.length > 0) {
        [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
    }
}

/**
 * Initialize Bootstrap popovers
 */
function initPopovers() {
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
    if (popoverTriggerList.length > 0) {
        [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl));
    }
}

/**
 * Initialize form validation
 */
function initFormValidation() {
    // Enable validation styles when attempting to submit a form
    const forms = document.querySelectorAll('.needs-validation');

    if (forms.length > 0) {
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            }, false);
        });
    }
}

/**
 * Auto-dismiss alerts after a delay
 */
function initAlertDismissal() {
    const autoAlerts = document.querySelectorAll('.alert.auto-dismiss');

    if (autoAlerts.length > 0) {
        autoAlerts.forEach(alert => {
            const delay = alert.dataset.dismissDelay || 5000;

            setTimeout(() => {
                const bsAlert = bootstrap.Alert.getInstance(alert);
                if (bsAlert) {
                    bsAlert.close();
                } else {
                    alert.classList.add('fade');
                    setTimeout(() => {
                        alert.remove();
                    }, 150);
                }
            }, delay);
        });
    }
}

/**
 * Initialize custom file inputs
 */
function initCustomFileInputs() {
    const fileInputs = document.querySelectorAll('.custom-file-input');

    if (fileInputs.length > 0) {
        fileInputs.forEach(input => {
            input.addEventListener('change', event => {
                const fileName = event.target.files[0]?.name;
                const label = input.nextElementSibling;

                if (label && fileName) {
                    label.textContent = fileName;
                }
            });
        });
    }
}

/**
 * Initialize image previews for file inputs
 */
function initImagePreviews() {
    const imageInputs = document.querySelectorAll('.image-input');

    if (imageInputs.length > 0) {
        imageInputs.forEach(input => {
            input.addEventListener('change', event => {
                const previewId = input.dataset.preview;
                const preview = document.getElementById(previewId);

                if (preview && event.target.files && event.target.files[0]) {
                    const reader = new FileReader();

                    reader.onload = e => {
                        preview.src = e.target.result;
                        preview.style.display = 'block';

                        // Hide "no preview" element if exists
                        const noPreview = document.getElementById(`no-${previewId}`);
                        if (noPreview) {
                            noPreview.style.display = 'none';
                        }
                    };

                    reader.readAsDataURL(event.target.files[0]);
                }
            });
        });
    }
}

/**
 * Initialize confirmation dialogs
 */
function initConfirmationDialogs() {
    const confirmButtons = document.querySelectorAll('[data-confirm]');

    if (confirmButtons.length > 0) {
        confirmButtons.forEach(button => {
            button.addEventListener('click', event => {
                const message = button.dataset.confirm || 'Are you sure you want to proceed?';

                if (!confirm(message)) {
                    event.preventDefault();
                    event.stopPropagation();
                }
            });
        });
    }
}

/**
 * Initialize currency formatting for input fields
 */
function initCurrencyFormatting() {
    const currencyInputs = document.querySelectorAll('.currency-input');

    if (currencyInputs.length > 0) {
        currencyInputs.forEach(input => {
            input.addEventListener('input', event => {
                // Remove any non-digit characters except decimal point
                let value = event.target.value.replace(/[^\d.]/g, '');

                // Format with thousands separators
                if (value) {
                    // Parse as float and format
                    value = parseFloat(value).toLocaleString('vi-VN');
                }

                event.target.value = value;
            });

            // Format initial value if exists
            if (input.value) {
                let value = input.value.replace(/[^\d.]/g, '');
                if (value) {
                    input.value = parseFloat(value).toLocaleString('vi-VN');
                }
            }
        });
    }
}

/**
 * Initialize sidebar toggle for mobile
 */
function initSidebarToggle() {
    const sidebarToggle = document.getElementById('sidebarToggle');

    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');

            // Store the state in localStorage
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });

        // Check for stored state on page load
        if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
            document.body.classList.add('sb-sidenav-toggled');
        }
    }
}

/**
 * Show loading spinner
 */
function showLoading() {
    const loadingDiv = document.createElement('div');
    loadingDiv.className = 'loading-indicator';
    loadingDiv.innerHTML = '<div class="spinner"></div>';
    document.body.appendChild(loadingDiv);
}

/**
 * Hide loading spinner
 */
function hideLoading() {
    const loadingIndicator = document.querySelector('.loading-indicator');
    if (loadingIndicator) {
        loadingIndicator.remove();
    }
}

/**
 * Format number as currency
 * @param {number} value - The number to format
 * @returns {string} Formatted currency string
 */
function formatCurrency(value) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0
    }).format(value);
}

/**
 * Format date in Vietnamese format
 * @param {string|Date} date - The date to format
 * @returns {string} Formatted date string
 */
function formatDate(date) {
    if (!date) return '';

    const dateObj = date instanceof Date ? date : new Date(date);

    return new Intl.DateTimeFormat('vi-VN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    }).format(dateObj);
}