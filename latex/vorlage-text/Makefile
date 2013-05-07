latexfile=APC-text

dvi :
	@latex $(latexfile)
	@bibtex $(latexfile)
	@latex $(latexfile)
	@latex $(latexfile)

ps : 
	@latex $(latexfile)
	@bibtex $(latexfile)
	@latex $(latexfile)
	@latex $(latexfile)
	@dvips $(latexfile)

pdf :
	@latex $(latexfile)
	@bibtex $(latexfile)
	@latex $(latexfile)
	@latex $(latexfile)
	@dvips $(latexfile)
	@ps2pdf $(latexfile).ps $(latexfile).pdf

latex :
	@latex $(latexfile)

clean :
	- @rm -f *.aux *.bbl *.blg *.dvi *.log *.pdf *.ps *.id *.nav *.snm *.out *.toc
