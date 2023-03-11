# PDFBoxRendering

# Platform
This is a Java Maven project

# Supported IDE
The project is created with Emacs, using Java language server (jdtls).
Critical setup includes

<pre>
;; JAVA development setup
(condition-case nil
    (require 'use-package)
  (file-error
   (require 'package)
   (add-to-list 'package-archives '("melpa" . "http://melpa.org/packages/"))
   (package-initialize)
   (package-refresh-contents)
   (package-install 'use-package)
   (setq use-package-always-ensure t)
   (require 'use-package)))

(use-package projectile)
(use-package flycheck)
(use-package yasnippet :config (yas-global-mode))
(use-package lsp-mode :hook ((lsp-mode . lsp-enable-which-key-integration)))
(use-package hydra)
(use-package company)
(use-package lsp-ui)
(use-package which-key :config (which-key-mode))
(use-package lsp-java :config (add-hook 'java-mode-hook 'lsp))
(use-package dap-mode :after lsp-mode :config (dap-auto-configure-mode))
(use-package dap-java :ensure nil)
(use-package helm-lsp)
(use-package helm
  :config (helm-mode))
(use-package lsp-treemacs)

(use-package lsp-java
  :defer 3
  :init
  ;;(setq-default lsp-java-server-install-dir "~/.emacs.d/lsp/server/eclipse.jdt.ls/")
  ;;(setq lsp-java-server-install-dir "/home/marek/.emacs.d/eclipse.jdt.ls/")
  (setq lsp-java-java-path "/usr/bin/java")
  ;; :config (lsp-java-spring-initializr)
  )

(add-hook 'dap-stopped-hook
          (lambda (arg) (call-interactively #'dap-hydra)))

(setq read-process-output-max (* 1024 1024)) ;; 1mb
(setq gc-cons-threshold (* 1024 1024 128))   ;; 100 million

(setenv "JAVA_HOME" "/usr/lib/jvm/java-11-openjdk-amd64/")

;; End of JAVA development setup
</pre>



