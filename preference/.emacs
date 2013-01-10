(prefer-coding-system 'chinese-gbk)
(prefer-coding-system 'utf-8)

;; For Linux ctrl�����ֿ��������С
(global-set-key (kbd "<C-mouse-4>") 'text-scale-increase)
(global-set-key (kbd "<C-mouse-5>") 'text-scale-decrease)

;;alt-;��ǿע�ͷ�ע��
(defun qiang-comment-dwim-line (&optional arg)
  "Replacement for the comment-dwim command.
If no region is selected and current line is not blank and
we are not at the end of the line, then comment current line.
Replaces default behaviour of comment-dwim,
when it inserts comment at the end of the line. "
  
  (interactive "*P")
  (comment-normalize-vars)
  
  (if (and (not (region-active-p)) (not (looking-at "[ \t]*$")))
      (comment-or-uncomment-region (line-beginning-position) (line-end-position))
    (comment-dwim arg))) 

(global-set-key "\M-;" 'qiang-comment-dwim-line)

;;alt-wĬ�ϸ��Ƶ�ǰ��
;; Smart copy, if no region active, it simply copy the current whole line
(defadvice kill-line (before check-position activate)
  (if (member major-mode
              '(emacs-lisp-mode scheme-mode lisp-mode
                                c-mode c++-mode objc-mode js-mode
                                latex-mode plain-tex-mode))
      (if (and (eolp) (not (bolp)))
          (progn (forward-char 1)
                 (just-one-space 0)
                 (backward-char 1)))))

(defadvice kill-ring-save (before slick-copy activate compile)
  "When called interactively with no active region, copy a single line instead."
  (interactive (if mark-active (list (region-beginning) (region-end))
                 (message "Copied line")
                 (list (line-beginning-position)
                       (line-beginning-position 2)))))

(defadvice kill-region (before slick-cut activate compile)
  "When called interactively with no active region, kill a single line instead."
  (interactive
   (if mark-active (list (region-beginning) (region-end))
     (list (line-beginning-position)
           (line-beginning-position 2)))))

;; Copy line from point to the end, exclude the line break
(defun qiang-copy-line (arg)
  "Copy lines (as many as prefix argument) in the kill ring"
  (interactive "p")
  (kill-ring-save (point)
                  (line-end-position))
  ;; (line-beginning-position (+ 1 arg)))
  (message "%d line%s copied" arg (if (= 1 arg) "" "s")))

(global-set-key (kbd "M-k") 'qiang-copy-line)



;;���������Զ���ʽ��
(dolist (command '(yank yank-pop))
  (eval
   `(defadvice, command (after indent-region activate)
      (and (not current-prefix-arg)
           (member major-mode
                   '(emacs-lisp-mode lisp-mode clojure-mode scheme-mode
                                     haskell-mode ruby-mode rspec-mode
                                     python-mode c-mode c++-mode objc-mode
                                     latex-mode js-mode plain-tex-mode))
           (let ((mark-even-if-inactive transient-mark-mode))
             (indent-region (region-beginning) (region-end) nil))))))


;;diredģʽ�б�ݵĸ�������ļ������ƿ�ݼ�
;; (defvar dired-selected-files nil
;;   "The selected files which is use to paste.")
;; (defvar dired-selected-for-cut nil
;;   "Indicate the selected files is cutted or copied.")
;; (defun dired-do-cut-copy ()
;;   "Do cut or copy"
;;   (let ((files (dired-get-marked-files 'no-dir)))
;;     (setq dired-selected-files ())
;;     (dolist (file files)
;;       (add-to-list 'dired-selected-files
;;                    (concat (dired-current-directory) file)))))
;; (dolist (src dired-selected-files)
;;   (let ((dst (concat (dired-current-directory)
;;                      (file-name-nondirectory src))))
;;     (if dired-selected-for-cut
;;         (dired-rename-file src dst t)
;;       (dired-copy-file src dst t))))
;; (define-key dired-mode-map "\C-w" 'dired-cut-marked)
;; (define-key dired-mode-map "\M-w" 'dired-copy-marked)
;; (define-key dired-mode-map "\C-y" 'dired-paste-selected)
;;diredģʽ�б�ݵĸ�������ļ������ƿ�ݼ�����


;;�Զ���ʾ�к�
;;(add-hook 'find-file-hook (lambda () (linum-mode 1)))
(global-linum-mode 1)

;;�����Զ�����
(setq indent-tabs-mode nil)
(setq default-tab-width 8)
(setq tab-width 8)
(setq tab-stop-list ())

;;color-theme �������
;;(add-to-list 'load-path "~/site-lisp/color-theme-6.6.0")
(require 'color-theme)
(color-theme-initialize)
;;(color-theme-robin-hood)
(color-theme-fischmeister)
;(color-theme-montz)

(defun qiang-font-existsp (font)
  (if (null (x-list-fonts font))
      nil
    t))
(defvar font-list '("Microsoft Yahei" "��Ȫ��ȿ�΢�׺�" "����" "������" "����"))

;;show line number
(global-linum-mode t)
(require 'cl) ;; find-if is in common list package
(find-if #'qiang-font-existsp font-list)

;;���ֱܷ�����Ӣ�ĺ��������壬�ǵ÷���color-themeѡ��֮��������߻Ḳ������
(defun qiang-make-font-string (font-name font-size)
  (if (and (stringp font-size)
           (equal ":" (string (elt font-size 0))))
      (format "%s%s" font-name font-size)
    (format "%s %s" font-name font-size)))
(defun qiang-set-font (english-fonts
                       english-font-size
                       chinese-fonts
                       &optional chinese-font-size)
  
  "english-font-size could be set to \":pixelsize=18\" or a integer.
If set/leave chinese-font-size to nil, it will follow english-font-size"
  (require 'cl) ; for find if
  (let ((en-font (qiang-make-font-string
                  (find-if #'qiang-font-existsp english-fonts)
                  english-font-size))
        (zh-font (font-spec :family (find-if #'qiang-font-existsp chinese-fonts)
                            :size chinese-font-size)))
    
    ;; Set the default English font
    ;;
    ;; The following 2 method cannot make the font settig work in new frames.
    ;; (set-default-font "Consolas:pixelsize=18")
    ;; (add-to-list 'default-frame-alist '(font . "Consolas:pixelsize=18"))
    ;; We have to use set-face-attribute
    (message "Set English Font to %s" en-font)
    (set-face-attribute 'default nil :font en-font)
    
    ;; Set Chinese font
    ;; Do not use 'unicode charset, it will cause the English font setting invalid
    (message "Set Chinese Font to %s" zh-font)
    (dolist (charset '(kana han symbol cjk-misc bopomofo))
      (set-fontset-font (frame-parameter nil 'font)
                        charset zh-font))))

(qiang-set-font
 '( "Monaco" "Ubuntu"  "Consolas" "DejaVu Sans Mono" "Monospace" "Courier New") ":pixelsize=16"
 '("��Ȫ��ȿ�΢�׺�" "Ubuntu" "Microsoft Yahei"  "����" "������" "����"))
;;���ֱܷ�����Ӣ�ĺ������������


;;emacswiki����el��չ������
(add-to-list 'load-path "~/.emacs.d")
(require 'install-elisp)
(setq install-elisp-repository-directory "~/.emacs.d/site-lisp")
(locate-library "auto-complete.el") 


(set-default 'tramp-default-proxies-alist (quote ((".*" "\\`root\\'" "/ssh:%h:"))))
;; Then you can simply type:
;; C-x C-f /sudo:root@host[#port]:/path/to/file
;/sudo:root@211.155.231.211#5200:/etc/passwd
                                        ;/sudo:root@*211#5200:/usr/local/nginx/conf/discuzx.conf
;;or (C-x C-f) and start the file name with ssh://server:/file.



;;svn psvn ���֧�֣���Ҫ��Ϊ��״̬����ߵ�Сͼ����ʾsvn״̬
(require 'psvn)
;; Start the svn interface with M-x svn-status

;; ��ibuffer����Ĭ�ϵ�buffer switch
;; �ο� http://www.emacswiki.org/emacs/IbufferMode
;; ibuffer��emacs�Դ��ģ����� M-x ibuffer ������
;; ����ֻ�ǽ���ݼ� C-x C-b ӳ��Ϊ����ibuffer������
;; ��ibuffer�еĲ�����ʽ��dired modeһ��
;; n��p�����£�m��ѡ�и��ļ�����ѡ�����D��kill buffer
;; �س����߰�e(����edit)���༭�ļ�
;; ��ibufferҳ�棬��Ӣ��б����ͨ���ؼ��ֹ�����С��ʾ�ļ���Χ
;; ���� �� /�ռ� �� ���ͻ�ֻ��ʾbuffer���������ռ��������ֵ�
;; / ����֧��������ʽ �� /200?
;; �������˰�����/�����ǰ� �� // ��
;; ��ibuffer�У���Ӣ�ĵȺ� �� = �� ��Ϊ�����ļ�������һ������汾��diff 
;; �� g ˢ���ļ�Ŀ¼
(global-set-key (kbd "C-x C-b") 'ibuffer)
(autoload 'ibuffer "ibuffer" "List buffers." t)

(load "php-mode")
(add-to-list 'auto-mode-alist
     	     '("\\.php[34]?\\'\\|\\.phtml\\'" . php-mode))
;;php �Զ���ʾ����
(add-hook 'php-mode-hook
	  (lambda ()
	    (require 'php-completion)
	    (php-completion-mode t)
	    (define-key php-mode-map (kbd "C-o") 'phpcmp-complete)))

(add-hook  'php-mode-hook
	   (lambda ()
	     (when (require 'auto-complete nil t)
	       (make-variable-buffer-local 'ac-sources)
	       (add-to-list 'ac-sources 'ac-source-php-completion)
	       ;; if you like patial match,
	       ;; use `ac-source-php-completion-patial' instead of `ac-source-php-completion'.
	       (add-to-list 'ac-sources 'ac-source-php-completion-patial)
	       (auto-complete-mode t)))) 


;; mmm-mode���ö��major mode���棬
;; ����html code���php code��������xhtml-mode ��php-mode�ֱ���ʾ
(add-to-list 'load-path "~/.emacs.d/mmm-mode-0.4.8")
(require 'mmm-auto)
(setq mmm-global-mode 'maybe)
(put 'dired-find-alternate-file 'disabled nil)

;; Display time
(display-time)
;; Make the mouse wheel scroll Emacs
(mouse-wheel-mode t)
;; Always end a file with a newline
(setq require-final-newline t)
;; Stop emacs from arbitrarily adding lines to the end of a file when the
;; cursor is moved past the end of it:
(setq next-line-add-newlines nil)
;; Flash instead of that annoying bell
(setq visible-bell t)
;; Remove icons toolbar
(if (> emacs-major-version 20)
    (tool-bar-mode -1))
;; Use y or n instead of yes or not
(fset 'yes-or-no-p 'y-or-n-p)
(show-paren-mode t)

;;����irc
(load-file "/usr/share/emacs/23.2/lisp/erc/erc.elc")
(setq erc-hide-list '("JOIN" "PART" "QUIT"))

;;slime-helper
(load (expand-file-name "~/quicklisp/slime-helper.el"))
;; Replace "sbcl" with the path to your implementation
(setq inferior-lisp-program "/usr/bin/sbcl")

;;�Զ�����ر�ʱ�򻷾�
(desktop-save-mode 1)
(setq x-select-enable-clipboard t)  ; makes killing/yanking interact with clipboard X11 selection
(put 'downcase-region 'disabled nil)
(put 'upcase-region 'disabled nil)
